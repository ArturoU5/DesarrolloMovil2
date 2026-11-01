package com.refreshpro.lavanderia.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.refreshpro.lavanderia.R
import com.refreshpro.lavanderia.data.repository.AuthRepository
import com.refreshpro.lavanderia.data.repository.PedidoRepository
import com.refreshpro.lavanderia.databinding.FragmentHomeBinding
import com.refreshpro.lavanderia.domain.model.EstadoPedido
import com.refreshpro.lavanderia.ui.mapa.MapaActivity
import com.refreshpro.lavanderia.ui.pedidos.NuevoPedidoActivity
import com.refreshpro.lavanderia.utils.Extras
import com.refreshpro.lavanderia.utils.ponerTitulo

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nombre = arguments?.getString(Extras.NOMBRE_USUARIO) ?: "usuario"
        binding.tvSaludo.text = "Hola, $nombre"
        binding.tvResumen.text =
            if (AuthRepository.esAdmin()) "Resumen de todos los pedidos" else "Resumen de tus pedidos"

        binding.btnVerPedidos.setOnClickListener { (activity as? HomeActivity)?.irA(R.id.nav_pedidos) }
        binding.btnServicios.setOnClickListener { (activity as? HomeActivity)?.irA(R.id.nav_servicios) }
        binding.btnNuevoPedido.setOnClickListener {
            startActivity(Intent(requireContext(), NuevoPedidoActivity::class.java))
        }
        binding.btnVerMapa.setOnClickListener {
            startActivity(Intent(requireContext(), MapaActivity::class.java))
        }
    }

    // Se actualiza el resumen cada vez que el fragment vuelve a primer plano
    override fun onResume() {
        super.onResume()
        ponerTitulo("Inicio")
        val lista = PedidoRepository.obtenerVisibles()
        binding.tvPendientes.text = lista.count { it.estadoPedido == EstadoPedido.PENDIENTE }.toString()
        binding.tvConfirmados.text = lista.count { it.estadoPedido == EstadoPedido.CONFIRMADO }.toString()
        binding.tvFinalizados.text = lista.count { it.estadoPedido == EstadoPedido.FINALIZADO }.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
