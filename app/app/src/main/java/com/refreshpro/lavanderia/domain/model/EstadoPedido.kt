package com.refreshpro.lavanderia.domain.model

enum class EstadoPedido(val etiqueta: String) {
    PENDIENTE("Pendiente"),
    CONFIRMADO("Confirmado"),
    FINALIZADO("Finalizado");

    /** Devuelve el siguiente estado del flujo, o null si ya está finalizado. */
    fun siguiente(): EstadoPedido? = values().getOrNull(ordinal + 1)
}
