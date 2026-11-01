package com.refreshpro.lavanderia.data.repository

import com.refreshpro.lavanderia.domain.model.Rol
import com.refreshpro.lavanderia.domain.model.Usuario

/** Autenticación local de demostración (sin servidor) con control de acceso por rol. */
object AuthRepository {
    private val usuarios = mutableListOf(
        Usuario("Admin RefreshPro", "admin@refreshpro.com", "123456", Rol.ADMIN),
        Usuario("María Pérez", "cliente@refreshpro.com", "123456", Rol.CLIENTE)
    )

    var usuarioActual: Usuario? = null
        private set

    fun iniciarSesion(correo: String, clave: String): Usuario? {
        val usuario = usuarios.find { it.correo.equals(correo, ignoreCase = true) && it.clave == clave }
        usuarioActual = usuario
        return usuario
    }

    /** Todo registro nuevo entra como CLIENTE. Devuelve false si el correo ya existe. */
    fun registrar(nombre: String, correo: String, clave: String): Boolean {
        if (usuarios.any { it.correo.equals(correo, ignoreCase = true) }) return false
        usuarios.add(Usuario(nombre, correo, clave, Rol.CLIENTE))
        return true
    }

    fun esAdmin(): Boolean = usuarioActual?.rol == Rol.ADMIN

    fun cerrarSesion() {
        usuarioActual = null
    }
}
