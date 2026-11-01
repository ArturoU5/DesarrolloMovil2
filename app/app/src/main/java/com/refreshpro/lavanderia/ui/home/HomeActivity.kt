package com.refreshpro.lavanderia.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.refreshpro.lavanderia.R
import com.refreshpro.lavanderia.data.repository.AuthRepository
import com.refreshpro.lavanderia.data.repository.PedidoRepository
import com.refreshpro.lavanderia.databinding.ActivityHomeBinding
import com.refreshpro.lavanderia.domain.model.EstadoPedido
import com.refreshpro.lavanderia.ui.BaseActivity
import com.refreshpro.lavanderia.ui.login.LoginActivity
import com.refreshpro.lavanderia.ui.mapa.MapaActivity
import com.refreshpro.lavanderia.ui.pedidos.NuevoPedidoActivity
import com.refreshpro.lavanderia.ui.servicios.ServiciosActivity
import com.refreshpro.lavanderia.ui.pedidos.PedidosActivity
import com.refreshpro.lavanderia.utils.Extras

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val nombre = intent.getStringExtra(Extras.NOMBRE_USUARIO) ?: "usuario"
        binding.tvSaludo.text = "Hola, $nombre"

        binding.btnVerPedidos.setOnClickListener {
            startActivity(Intent(this, PedidosActivity::class.java))
        }
        binding.btnNuevoPedido.setOnClickListener {
            startActivity(Intent(this, NuevoPedidoActivity::class.java))
        }
        binding.btnServicios.setOnClickListener {
            startActivity(Intent(this, ServiciosActivity::class.java))
        }
        binding.btnVerMapa.setOnClickListener {
            startActivity(Intent(this, MapaActivity::class.java))
        }
    }

    // Se actualiza el resumen cada vez que la pantalla vuelve a primer plano
    override fun onResume() {
        super.onResume()
        val lista = PedidoRepository.obtenerTodos()
        binding.tvPendientes.text = lista.count { it.estadoPedido == EstadoPedido.PENDIENTE }.toString()
        binding.tvConfirmados.text = lista.count { it.estadoPedido == EstadoPedido.CONFIRMADO }.toString()
        binding.tvFinalizados.text = lista.count { it.estadoPedido == EstadoPedido.FINALIZADO }.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            AuthRepository.cerrarSesion()
            startActivity(
                Intent(this, LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
