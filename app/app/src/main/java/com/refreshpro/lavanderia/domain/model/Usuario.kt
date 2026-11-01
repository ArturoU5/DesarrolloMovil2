package com.refreshpro.lavanderia.domain.model

data class Usuario(
    val nombre: String,
    val correo: String,
    val clave: String,
    val rol: Rol = Rol.CLIENTE
)
