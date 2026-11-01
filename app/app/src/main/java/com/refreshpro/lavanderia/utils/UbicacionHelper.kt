package com.refreshpro.lavanderia.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

/** Gestiona permisos de ubicación y obtiene la posición actual (o una simulada). */
object UbicacionHelper {

    // Plaza de Armas de Tumbes (ubicación simulada si no hay permiso o GPS)
    const val SIMULADA_LAT = -3.5669
    const val SIMULADA_LNG = -80.4515

    val PERMISOS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun tienePermiso(context: Context): Boolean = PERMISOS.any {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    /** onResultado(latitud, longitud, esSimulada) */
    @SuppressLint("MissingPermission")
    fun obtenerUbicacion(context: Context, onResultado: (Double, Double, Boolean) -> Unit) {
        if (!tienePermiso(context)) {
            onResultado(SIMULADA_LAT, SIMULADA_LNG, true)
            return
        }
        LocationServices.getFusedLocationProviderClient(context)
            .getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, CancellationTokenSource().token)
            .addOnSuccessListener { loc ->
                if (loc != null) onResultado(loc.latitude, loc.longitude, false)
                else onResultado(SIMULADA_LAT, SIMULADA_LNG, true)
            }
            .addOnFailureListener { onResultado(SIMULADA_LAT, SIMULADA_LNG, true) }
    }
}
