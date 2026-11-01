package com.refreshpro.lavanderia.domain.model

data class Pedido(
    val id: Int,
    val clienteNombre: String,
    val telefono: String,
    val servicio: String,
    val fechaRecojo: String,
    val cantidadPrendas: Int,
    val estadoPedido: EstadoPedido,
    val puntoRecojo: String,
    val latitud: Double,
    val longitud: Double
)
