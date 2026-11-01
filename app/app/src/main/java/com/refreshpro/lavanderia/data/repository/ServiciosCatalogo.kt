package com.refreshpro.lavanderia.data.repository

import com.refreshpro.lavanderia.domain.model.Servicio

object ServiciosCatalogo {
    val servicios = listOf(
        Servicio(1, "Lavado por kilo", "Lavado y secado de ropa de uso diario, doblada y embolsada.", 6.0, "por kilo"),
        Servicio(2, "Lavado en seco", "Cuidado especial para trajes, abrigos y prendas delicadas.", 25.0, "por prenda"),
        Servicio(3, "Planchado", "Planchado profesional, listo para colgar o guardar.", 3.5, "por prenda"),
        Servicio(4, "Edredones y cobertores", "Lavado profundo de piezas grandes con secado completo.", 35.0, "por pieza"),
        Servicio(5, "Lavado de zapatillas", "Limpieza, desinfección y secado de zapatillas.", 20.0, "por par")
    )

    val nombres: List<String> get() = servicios.map { it.nombre }

    fun obtenerPorId(id: Int): Servicio? = servicios.find { it.id == id }
}
