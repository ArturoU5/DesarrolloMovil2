package com.refreshpro.lavanderia.ui.servicios

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.refreshpro.lavanderia.data.repository.ServiciosCatalogo
import com.refreshpro.lavanderia.databinding.FragmentServiciosBinding
import com.refreshpro.lavanderia.utils.Extras
import com.refreshpro.lavanderia.utils.ponerTitulo

class ServiciosFragment : Fragment() {

    private var _binding: FragmentServiciosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiciosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ServicioAdapter { servicio ->
            startActivity(
                Intent(requireContext(), ServicioDetalleActivity::class.java)
                    .putExtra(Extras.SERVICIO_ID, servicio.id)
            )
        }
        binding.rvServicios.layoutManager = LinearLayoutManager(requireContext())
        binding.rvServicios.adapter = adapter
        adapter.submitList(ServiciosCatalogo.servicios)
    }

    override fun onResume() {
        super.onResume()
        ponerTitulo("Servicios")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
