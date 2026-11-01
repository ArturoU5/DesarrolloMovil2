package com.refreshpro.lavanderia.ui.contacto

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.refreshpro.lavanderia.databinding.FragmentContactosBinding
import com.refreshpro.lavanderia.utils.ponerTitulo

class ContactosFragment : Fragment() {

    private var _binding: FragmentContactosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLlamar.setOnClickListener {
            abrir(Intent(Intent.ACTION_DIAL, Uri.parse("tel:+51987654321")))
        }
        binding.btnWhatsapp.setOnClickListener {
            abrir(Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/51987654321")))
        }
        binding.btnCorreo.setOnClickListener {
            abrir(
                Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:contacto@refreshpro.com"))
                    .putExtra(Intent.EXTRA_SUBJECT, "Consulta RefreshPro Clean")
            )
        }
    }

    private fun abrir(intent: Intent) {
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "No hay una app para esta acción", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        ponerTitulo("Contáctanos")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
