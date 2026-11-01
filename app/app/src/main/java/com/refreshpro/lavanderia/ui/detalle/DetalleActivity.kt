package com.refreshpro.lavanderia.ui.detalle

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.refreshpro.lavanderia.data.repository.PedidoRepository
import com.refreshpro.lavanderia.databinding.ActivityDetalleBinding
import com.refreshpro.lavanderia.ui.BaseActivity
import com.refreshpro.lavanderia.ui.mapa.MapaActivity
import com.refreshpro.lavanderia.utils.Extras
import com.refreshpro.lavanderia.utils.colorRes
import java.util.Locale

class DetalleActivity : BaseActivity() {

    private lateinit var binding: ActivityDetalleBinding
    private var pedidoId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pedidoId = intent.getIntExtra(Extras.PEDIDO_ID, -1)

        binding.btnVerMapa.setOnClickListener {
            startActivity(Intent(this, MapaActivity::class.java).putExtra(Extras.PEDIDO_ID, pedidoId))
        }
        binding.btnAvanzar.setOnClickListener {
            PedidoRepository.avanzarEstado(pedidoId)
            mostrarPedido()
        }
    }

    override fun onResume() {
        super.onResume()
        mostrarPedido()
    }

    private fun mostrarPedido() {
        val pedido = PedidoRepository.obtenerPorId(pedidoId)
        if (pedido == null) {
            finish()
            return
        }
        with(binding) {
            tvCodigo.text = "Pedido #${pedido.id}"
            tvEstado.text = pedido.estadoPedido.etiqueta
            val color = ContextCompat.getColor(this@DetalleActivity, pedido.estadoPedido.colorRes())
            tvEstado.backgroundTintList = ColorStateList.valueOf(color)
            tvCliente.text = pedido.clienteNombre
            tvTelefono.text = pedido.telefono
            tvServicio.text = pedido.servicio
            tvFecha.text = pedido.fechaRecojo
            tvPrendas.text = pedido.cantidadPrendas.toString()
            tvDireccion.text = pedido.puntoRecojo
            tvCoordenadas.text = String.format(Locale.US, "%.5f, %.5f", pedido.latitud, pedido.longitud)

            val siguiente = pedido.estadoPedido.siguiente()
            if (siguiente == null) {
                btnAvanzar.visibility = View.GONE
            } else {
                btnAvanzar.visibility = View.VISIBLE
                btnAvanzar.text = "Marcar como ${siguiente.etiqueta}"
            }
        }
    }
}
