package com.refreshpro.lavanderia.ui.servicios

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.refreshpro.lavanderia.data.repository.ServiciosCatalogo
import com.refreshpro.lavanderia.databinding.ActivityServiciosBinding
import com.refreshpro.lavanderia.ui.BaseActivity
import com.refreshpro.lavanderia.utils.Extras

class ServiciosActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityServiciosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = ServicioAdapter { servicio ->
            startActivity(
                Intent(this, ServicioDetalleActivity::class.java)
                    .putExtra(Extras.SERVICIO_ID, servicio.id)
            )
        }
        binding.rvServicios.layoutManager = LinearLayoutManager(this)
        binding.rvServicios.adapter = adapter
        adapter.submitList(ServiciosCatalogo.servicios)
    }
}
