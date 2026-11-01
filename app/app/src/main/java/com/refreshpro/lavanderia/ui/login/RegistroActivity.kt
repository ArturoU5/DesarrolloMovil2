package com.refreshpro.lavanderia.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.refreshpro.lavanderia.data.repository.AuthRepository
import com.refreshpro.lavanderia.databinding.ActivityRegistroBinding
import com.refreshpro.lavanderia.ui.BaseActivity
import com.refreshpro.lavanderia.utils.Validaciones

class RegistroActivity : BaseActivity() {

    private lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.etNombre.doAfterTextChanged { binding.tilNombre.error = null }
        binding.etCorreo.doAfterTextChanged { binding.tilCorreo.error = null }
        binding.etClave.doAfterTextChanged { binding.tilClave.error = null }
        binding.etConfirmar.doAfterTextChanged { binding.tilConfirmar.error = null }
        binding.btnRegistrar.setOnClickListener { registrar() }
    }

    private fun registrar() {
        val nombre = binding.etNombre.text.toString().trim()
        val correo = binding.etCorreo.text.toString().trim()
        val clave = binding.etClave.text.toString()
        val confirmar = binding.etConfirmar.text.toString()

        val errores = listOf(
            binding.tilNombre to Validaciones.nombre(nombre),
            binding.tilCorreo to Validaciones.correo(correo),
            binding.tilClave to Validaciones.clave(clave),
            binding.tilConfirmar to Validaciones.confirmarClave(clave, confirmar)
        )
        errores.forEach { (campo, error) -> campo.error = error }
        if (errores.any { it.second != null }) return

        if (!AuthRepository.registrar(nombre, correo, clave)) {
            binding.tilCorreo.error = "Este correo ya está registrado"
            return
        }
        Toast.makeText(this, "Cuenta creada. Ya puedes iniciar sesión", Toast.LENGTH_LONG).show()
        finish()
    }
}
