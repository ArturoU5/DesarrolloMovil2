package com.refreshpro.lavanderia.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.refreshpro.lavanderia.R
import com.refreshpro.lavanderia.data.repository.PedidoRepository
import com.refreshpro.lavanderia.databinding.FragmentAdminBinding
import com.refreshpro.lavanderia.domain.model.EstadoPedido
import com.refreshpro.lavanderia.ui.home.HomeActivity
import com.refreshpro.lavanderia.utils.ponerTitulo

/** Panel solo para el rol ADMIN: estadísticas generales de todos los pedidos. */
class AdminFragment : Fragment() {

    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGestionar.setOnClickListener {
            (activity as? HomeActivity)?.irA(R.id.nav_pedidos)
        }
    }

    override fun onResume() {
        super.onResume()
        ponerTitulo("Panel de administración")
        val lista = PedidoRepository.obtenerVisibles() // para el ADMIN son todos
        binding.tvTotalPedidos.text = lista.size.toString()
        binding.tvTotalPrendas.text = lista.sumOf { it.cantidadPrendas }.toString()
        binding.tvTotalClientes.text = lista.map { it.clienteNombre }.distinct().size.toString()
        binding.tvAdminPendientes.text = lista.count { it.estadoPedido == EstadoPedido.PENDIENTE }.toString()
        binding.tvAdminConfirmados.text = lista.count { it.estadoPedido == EstadoPedido.CONFIRMADO }.toString()
        binding.tvAdminFinalizados.text = lista.count { it.estadoPedido == EstadoPedido.FINALIZADO }.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
