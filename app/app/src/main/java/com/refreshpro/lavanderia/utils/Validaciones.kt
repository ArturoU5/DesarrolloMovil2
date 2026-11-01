package com.refreshpro.lavanderia.utils

import android.util.Patterns

/** Cada función devuelve el mensaje de error, o null si el valor es válido. */
object Validaciones {

    fun correo(v: String): String? = when {
        v.isBlank() -> "Ingresa tu correo"
        !Patterns.EMAIL_ADDRESS.matcher(v).matches() -> "Correo no válido"
        else -> null
    }

    fun clave(v: String): String? = when {
        v.isBlank() -> "Ingresa tu contraseña"
        v.length < 6 -> "Mínimo 6 caracteres"
        else -> null
    }

    fun confirmarClave(clave: String, confirmacion: String): String? = when {
        confirmacion.isBlank() -> "Confirma tu contraseña"
        clave != confirmacion -> "Las contraseñas no coinciden"
        else -> null
    }

    fun nombre(v: String): String? = when {
        v.isBlank() -> "Ingresa el nombre del cliente"
        v.length < 3 -> "Mínimo 3 caracteres"
        else -> null
    }

    fun telefono(v: String): String? = when {
        v.isBlank() -> "Ingresa el teléfono"
        !Regex("^9\\d{8}$").matches(v) -> "Debe tener 9 dígitos y empezar con 9"
        else -> null
    }

    fun cantidad(v: String): String? {
        val n = v.toIntOrNull()
        return when {
            n == null -> "Ingresa un número"
            n !in 1..100 -> "Entre 1 y 100 prendas"
            else -> null
        }
    }

    fun obligatorio(v: String, mensaje: String): String? = if (v.isBlank()) mensaje else null
}
