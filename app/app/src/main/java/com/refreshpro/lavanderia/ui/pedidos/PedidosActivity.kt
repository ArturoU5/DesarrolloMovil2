package com.refreshpro.lavanderia.ui.pedidos

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.refreshpro.lavanderia.data.repository.PedidoRepository
import com.refreshpro.lavanderia.databinding.ActivityPedidosBinding
import com.refreshpro.lavanderia.ui.BaseActivity
import com.refreshpro.lavanderia.ui.detalle.DetalleActivity
import com.refreshpro.lavanderia.utils.Extras

class PedidosActivity : BaseActivity() {

    private lateinit var binding: ActivityPedidosBinding
    private lateinit var adapter: PedidoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPedidosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = PedidoAdapter { pedido ->
            val intent = Intent(this, DetalleActivity::class.java)
                .putExtra(Extras.PEDIDO_ID, pedido.id)
            startActivity(intent)
        }
        binding.rvPedidos.layoutManager = LinearLayoutManager(this)
        binding.rvPedidos.adapter = adapter

        binding.fabNuevo.setOnClickListener {
            startActivity(Intent(this, NuevoPedidoActivity::class.java))
        }
    }

    // Recarga la lista al volver (por ejemplo, tras crear un pedido)
    override fun onResume() {
        super.onResume()
        val lista = PedidoRepository.obtenerTodos()
        adapter.submitList(lista)
        binding.tvVacio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
    }
}
