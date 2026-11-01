package com.refreshpro.lavanderia.data.repository

import com.refreshpro.lavanderia.domain.model.EstadoPedido
import com.refreshpro.lavanderia.domain.model.Pedido
import com.refreshpro.lavanderia.domain.model.Rol

/** Repositorio en memoria con datos de ejemplo (coordenadas aproximadas de Tumbes). */
object PedidoRepository {

    private val pedidos = mutableListOf(
        Pedido(1, "María Pérez", "987654321", "Lavado por kilo", "20/10/2026", 12,
            EstadoPedido.PENDIENTE, "Av. Tumbes Norte 245, Tumbes", -3.5712, -80.4551,
            "cliente@refreshpro.com"),
        Pedido(2, "María Pérez", "987654321", "Lavado en seco", "21/10/2026", 3,
            EstadoPedido.CONFIRMADO, "Malecón Benavides, Tumbes", -3.5619, -80.4592,
            "cliente@refreshpro.com"),
        Pedido(3, "María Pérez", "987654321", "Edredones y cobertores", "18/10/2026", 2,
            EstadoPedido.FINALIZADO, "Urb. Andrés Araujo, Tumbes", -3.5750, -80.4480,
            "cliente@refreshpro.com"),
        Pedido(4, "Jorge Silva", "954321098", "Planchado", "22/10/2026", 20,
            EstadoPedido.PENDIENTE, "Av. Mcal. Castilla 120, Tumbes", -3.5690, -80.4450,
            "jorge@correo.com")
    )
    private var siguienteId = 5

    /** ADMIN ve todos los pedidos; CLIENTE solo los suyos. */
    fun obtenerVisibles(): List<Pedido> {
        val usuario = AuthRepository.usuarioActual ?: return emptyList()
        return if (usuario.rol == Rol.ADMIN) {
            pedidos.toList()
        } else {
            pedidos.filter { it.clienteCorreo.equals(usuario.correo, ignoreCase = true) }
        }
    }

    fun obtenerPorId(id: Int): Pedido? = obtenerVisibles().find { it.id == id }

    fun agregar(
        cliente: String, telefono: String, servicio: String, fecha: String,
        cantidad: Int, direccion: String, latitud: Double, longitud: Double,
        clienteCorreo: String
    ): Pedido {
        val nuevo = Pedido(
            siguienteId++, cliente, telefono, servicio, fecha, cantidad,
            EstadoPedido.PENDIENTE, direccion, latitud, longitud, clienteCorreo
        )
        pedidos.add(0, nuevo)
        return nuevo
    }

    /** Solo el ADMIN puede cambiar el estado de un pedido. */
    fun avanzarEstado(id: Int) {
        if (!AuthRepository.esAdmin()) return
        val indice = pedidos.indexOfFirst { it.id == id }
        if (indice == -1) return
        val actual = pedidos[indice]
        val siguiente = actual.estadoPedido.siguiente() ?: return
        pedidos[indice] = actual.copy(estadoPedido = siguiente)
    }
}
