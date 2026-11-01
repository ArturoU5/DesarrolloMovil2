package com.refreshpro.lavanderia.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/** Cambia el título de la Toolbar de la Activity que contiene al Fragment. */
fun Fragment.ponerTitulo(titulo: String) {
    (activity as? AppCompatActivity)?.supportActionBar?.title = titulo
}
