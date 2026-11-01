package com.refreshpro.lavanderia.ui.mapa

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.refreshpro.lavanderia.data.repository.PedidoRepository
import com.refreshpro.lavanderia.databinding.ActivityMapaBinding
import com.refreshpro.lavanderia.domain.model.Pedido
import com.refreshpro.lavanderia.ui.BaseActivity
import com.refreshpro.lavanderia.utils.Extras
import com.refreshpro.lavanderia.utils.UbicacionHelper
import java.util.Locale

/**
 * Muestra el punto de recojo de un pedido (si recibe PEDIDO_ID)
 * o todos los pedidos, junto con la ubicación del usuario.
 */
class MapaActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapaBinding
    private var mapa: GoogleMap? = null
    private var pedido: Pedido? = null

    private val launcherPermisos = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultado ->
        if (resultado.values.any { it }) {
            habilitarMiUbicacion()
        } else {
            Snackbar.make(binding.root, "Permiso denegado: se usará una ubicación simulada", Snackbar.LENGTH_LONG).show()
        }
        mostrarUbicacionUsuario()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getIntExtra(Extras.PEDIDO_ID, -1)
        pedido = PedidoRepository.obtenerPorId(id)
        supportActionBar?.title = if (pedido != null) "Punto de recojo" else "Mapa de recojos"

        val fragmento = supportFragmentManager.findFragmentById(binding.mapFragment.id) as SupportMapFragment
        fragmento.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapa = googleMap
        googleMap.uiSettings.isZoomControlsEnabled = true
        dibujarPedidos(googleMap)

        if (UbicacionHelper.tienePermiso(this)) {
            habilitarMiUbicacion()
            mostrarUbicacionUsuario()
        } else {
            launcherPermisos.launch(UbicacionHelper.PERMISOS)
        }
    }

    private fun dibujarPedidos(googleMap: GoogleMap) {
        val lista = pedido?.let { listOf(it) } ?: PedidoRepository.obtenerVisibles()
        if (lista.isEmpty()) {
            binding.tvInfo.text = "No hay pedidos para mostrar"
            return
        }

        val limites = LatLngBounds.Builder()
        lista.forEach { p ->
            val posicion = LatLng(p.latitud, p.longitud)
            googleMap.addMarker(
                MarkerOptions()
                    .position(posicion)
                    .title("${p.clienteNombre} - ${p.servicio}")
                    .snippet(p.puntoRecojo)
            )
            limites.include(posicion)
        }

        if (lista.size == 1) {
            val p = lista.first()
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(p.latitud, p.longitud), 15f))
            binding.tvInfo.text = "Punto de recojo: ${p.puntoRecojo}"
        } else {
            binding.tvInfo.text = "${lista.size} puntos de recojo registrados"
            googleMap.setOnMapLoadedCallback {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(limites.build(), 150))
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun habilitarMiUbicacion() {
        mapa?.isMyLocationEnabled = true
    }

    private fun mostrarUbicacionUsuario() {
        UbicacionHelper.obtenerUbicacion(this) { lat, lng, simulada ->
            val googleMap = mapa ?: return@obtenerUbicacion
            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(lat, lng))
                    .title(if (simulada) "Ubicación simulada" else "Tu ubicación")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
            )
            pedido?.let { p ->
                val resultado = FloatArray(1)
                Location.distanceBetween(lat, lng, p.latitud, p.longitud, resultado)
                val km = resultado[0] / 1000
                val tipo = if (simulada) "simulada" else "actual"
                binding.tvInfo.text = String.format(
                    Locale.US, "Punto de recojo: %s\nDistancia desde tu ubicación %s: %.1f km",
                    p.puntoRecojo, tipo, km
                )
            }
        }
    }
}
