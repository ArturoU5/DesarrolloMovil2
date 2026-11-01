package com.refreshpro.lavanderia.utils

import androidx.annotation.ColorRes
import com.refreshpro.lavanderia.R
import com.refreshpro.lavanderia.domain.model.EstadoPedido

@ColorRes
fun EstadoPedido.colorRes(): Int = when (this) {
    EstadoPedido.PENDIENTE -> R.color.estado_pendiente
    EstadoPedido.CONFIRMADO -> R.color.estado_confirmado
    EstadoPedido.FINALIZADO -> R.color.estado_finalizado
}
