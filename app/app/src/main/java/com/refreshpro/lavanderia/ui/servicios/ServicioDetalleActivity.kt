package com.refreshpro.lavanderia.ui.servicios

import android.content.Intent
import android.os.Bundle
import com.refreshpro.lavanderia.data.repository.ServiciosCatalogo
import com.refreshpro.lavanderia.databinding.ActivityServicioDetalleBinding
import com.refreshpro.lavanderia.ui.BaseActivity
import com.refreshpro.lavanderia.ui.pedidos.NuevoPedidoActivity
import com.refreshpro.lavanderia.utils.Extras
import java.util.Locale

class ServicioDetalleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityServicioDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val servicio = ServiciosCatalogo.obtenerPorId(intent.getIntExtra(Extras.SERVICIO_ID, -1))
        if (servicio == null) {
            finish()
            return
        }

        binding.tvNombre.text = servicio.nombre
        binding.tvPrecio.text = String.format(Locale.US, "S/ %.2f %s", servicio.precio, servicio.unidad)
        binding.tvDescripcion.text = servicio.descripcion

        binding.btnSolicitar.setOnClickListener {
            startActivity(
                Intent(this, NuevoPedidoActivity::class.java)
                    .putExtra(Extras.SERVICIO_NOMBRE, servicio.nombre)
            )
        }
    }
}
