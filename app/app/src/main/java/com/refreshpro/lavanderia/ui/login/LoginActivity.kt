package com.refreshpro.lavanderia.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import com.refreshpro.lavanderia.data.repository.AuthRepository
import com.refreshpro.lavanderia.databinding.ActivityLoginBinding
import com.refreshpro.lavanderia.ui.BaseActivity
import com.refreshpro.lavanderia.ui.home.HomeActivity
import com.refreshpro.lavanderia.utils.Extras
import com.refreshpro.lavanderia.utils.Validaciones

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etCorreo.doAfterTextChanged { binding.tilCorreo.error = null }
        binding.etClave.doAfterTextChanged { binding.tilClave.error = null }
        binding.btnIngresar.setOnClickListener { iniciarSesion() }
        binding.btnRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }

    private fun iniciarSesion() {
        val correo = binding.etCorreo.text.toString().trim()
        val clave = binding.etClave.text.toString()

        val errorCorreo = Validaciones.correo(correo)
        val errorClave = Validaciones.clave(clave)
        binding.tilCorreo.error = errorCorreo
        binding.tilClave.error = errorClave
        if (errorCorreo != null || errorClave != null) return

        val usuario = AuthRepository.iniciarSesion(correo, clave)
        if (usuario == null) {
            Snackbar.make(binding.root, "Correo o contraseña incorrectos", Snackbar.LENGTH_LONG).show()
            return
        }

        // Envío de datos entre pantallas
        val intent = Intent(this, HomeActivity::class.java)
            .putExtra(Extras.NOMBRE_USUARIO, usuario.nombre)
        startActivity(intent)
        finish()
    }
}
