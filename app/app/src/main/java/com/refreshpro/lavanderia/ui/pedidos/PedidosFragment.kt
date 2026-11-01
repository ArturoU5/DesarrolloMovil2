package com.refreshpro.lavanderia.ui.pedidos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.refreshpro.lavanderia.data.repository.AuthRepository
import com.refreshpro.lavanderia.data.repository.PedidoRepository
import com.refreshpro.lavanderia.databinding.FragmentPedidosBinding
import com.refreshpro.lavanderia.ui.detalle.DetalleActivity
import com.refreshpro.lavanderia.utils.Extras
import com.refreshpro.lavanderia.utils.ponerTitulo

class PedidosFragment : Fragment() {

    private var _binding: FragmentPedidosBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PedidoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPedidosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PedidoAdapter { pedido ->
            val intent = Intent(requireContext(), DetalleActivity::class.java)
                .putExtra(Extras.PEDIDO_ID, pedido.id)
            startActivity(intent)
        }
        binding.rvPedidos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPedidos.adapter = adapter

        binding.fabNuevo.setOnClickListener {
            startActivity(Intent(requireContext(), NuevoPedidoActivity::class.java))
        }
    }

    // Recarga la lista al volver (por ejemplo, tras crear un pedido)
    override fun onResume() {
        super.onResume()
        ponerTitulo(if (AuthRepository.esAdmin()) "Todos los pedidos" else "Mis pedidos")
        val lista = PedidoRepository.obtenerVisibles()
        adapter.submitList(lista)
        binding.tvVacio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
