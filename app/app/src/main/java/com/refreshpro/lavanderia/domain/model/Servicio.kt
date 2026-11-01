package com.refreshpro.lavanderia.domain.model

data class Servicio(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val unidad: String
)
