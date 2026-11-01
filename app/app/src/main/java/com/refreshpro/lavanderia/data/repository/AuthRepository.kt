package com.refreshpro.lavanderia.data.repository

import com.refreshpro.lavanderia.domain.model.Usuario

/** Autenticación local de demostración (sin servidor). */
object AuthRepository {
    private val usuarios = mutableListOf(
        Usuario("Admin RefreshPro", "admin@refreshpro.com", "123456"),
        Usuario("Cliente Demo", "cliente@refreshpro.com", "123456")
    )

    var usuarioActual: Usuario? = null
        private set

    fun iniciarSesion(correo: String, clave: String): Usuario? {
        val usuario = usuarios.find { it.correo.equals(correo, ignoreCase = true) && it.clave == clave }
        usuarioActual = usuario
        return usuario
    }

    /** Registra un cliente nuevo. Devuelve false si el correo ya existe. */
    fun registrar(nombre: String, correo: String, clave: String): Boolean {
        if (usuarios.any { it.correo.equals(correo, ignoreCase = true) }) return false
        usuarios.add(Usuario(nombre, correo, clave))
        return true
    }

    fun cerrarSesion() {
        usuarioActual = null
    }
}
