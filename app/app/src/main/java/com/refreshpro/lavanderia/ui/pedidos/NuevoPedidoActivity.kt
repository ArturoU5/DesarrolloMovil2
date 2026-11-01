package com.refreshpro.lavanderia.ui.pedidos

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.refreshpro.lavanderia.data.repository.PedidoRepository
import com.refreshpro.lavanderia.data.repository.ServiciosCatalogo
import com.refreshpro.lavanderia.databinding.ActivityNuevoPedidoBinding
import com.refreshpro.lavanderia.ui.BaseActivity
import com.refreshpro.lavanderia.utils.Extras
import com.refreshpro.lavanderia.utils.UbicacionHelper
import com.refreshpro.lavanderia.utils.Validaciones
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class NuevoPedidoActivity : BaseActivity() {

    private lateinit var binding: ActivityNuevoPedidoBinding
    private var latitud: Double? = null
    private var longitud: Double? = null

    private val launcherPermisos = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultado ->
        if (resultado.values.none { it }) {
            Snackbar.make(binding.root, "Permiso denegado: se usará una ubicación simulada", Snackbar.LENGTH_LONG).show()
        }
        obtenerUbicacion()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevoPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.actServicio.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_list_item_1, ServiciosCatalogo.nombres)
        )

        // Si viene desde el detalle de un servicio, ya queda seleccionado
        intent.getStringExtra(Extras.SERVICIO_NOMBRE)?.let { binding.actServicio.setText(it, false) }

        // Los errores desaparecen al volver a escribir
        binding.etNombre.doAfterTextChanged { binding.tilNombre.error = null }
        binding.etTelefono.doAfterTextChanged { binding.tilTelefono.error = null }
        binding.etCantidad.doAfterTextChanged { binding.tilCantidad.error = null }
        binding.etDireccion.doAfterTextChanged { binding.tilDireccion.error = null }
        binding.actServicio.setOnItemClickListener { _, _, _, _ -> binding.tilServicio.error = null }

        binding.etFecha.setOnClickListener { mostrarCalendario() }
        binding.btnUbicacion.setOnClickListener {
            if (UbicacionHelper.tienePermiso(this)) obtenerUbicacion()
            else launcherPermisos.launch(UbicacionHelper.PERMISOS)
        }
        binding.btnGuardar.setOnClickListener { guardar() }
    }

    private fun mostrarCalendario() {
        val restricciones = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())
            .build()
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Fecha de recojo")
            .setCalendarConstraints(restricciones)
            .build()
        picker.addOnPositiveButtonClickListener { millis ->
            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formato.timeZone = TimeZone.getTimeZone("UTC")
            binding.etFecha.setText(formato.format(Date(millis)))
            binding.tilFecha.error = null
        }
        picker.show(supportFragmentManager, "fecha")
    }

    private fun obtenerUbicacion() {
        UbicacionHelper.obtenerUbicacion(this) { lat, lng, simulada ->
            latitud = lat
            longitud = lng
            val tipo = if (simulada) "simulada" else "actual"
            binding.tvCoordenadas.text = String.format(Locale.US, "Ubicación %s: %.5f, %.5f", tipo, lat, lng)
        }
    }

    private fun guardar() {
        val nombre = binding.etNombre.text.toString().trim()
        val telefono = binding.etTelefono.text.toString().trim()
        val servicio = binding.actServicio.text.toString().trim()
        val fecha = binding.etFecha.text.toString().trim()
        val cantidad = binding.etCantidad.text.toString().trim()
        val direccion = binding.etDireccion.text.toString().trim()

        val errores = listOf(
            binding.tilNombre to Validaciones.nombre(nombre),
            binding.tilTelefono to Validaciones.telefono(telefono),
            binding.tilServicio to Validaciones.obligatorio(servicio, "Selecciona un servicio"),
            binding.tilFecha to Validaciones.obligatorio(fecha, "Selecciona la fecha"),
            binding.tilCantidad to Validaciones.cantidad(cantidad),
            binding.tilDireccion to Validaciones.obligatorio(direccion, "Ingresa la dirección")
        )
        errores.forEach { (campo, error) -> campo.error = error }
        val hayErrores = errores.any { it.second != null }

        val lat = latitud
        val lng = longitud
        if (lat == null || lng == null) {
            Snackbar.make(binding.root, "Marca la ubicación de recojo", Snackbar.LENGTH_LONG).show()
            return
        }
        if (hayErrores) return

        PedidoRepository.agregar(nombre, telefono, servicio, fecha, cantidad.toInt(), direccion, lat, lng)
        Toast.makeText(this, "Pedido registrado", Toast.LENGTH_SHORT).show()
        finish()
    }
}
