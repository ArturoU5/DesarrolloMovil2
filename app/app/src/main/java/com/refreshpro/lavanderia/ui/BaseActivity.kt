package com.refreshpro.lavanderia.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/** Activity base: registra el ciclo de vida en Logcat (filtrar por "CicloVida"). */
abstract class BaseActivity : AppCompatActivity() {

    private val nombre: String get() = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CicloVida", "$nombre -> onCreate")
    }

    override fun onStart() { super.onStart(); Log.d("CicloVida", "$nombre -> onStart") }
    override fun onResume() { super.onResume(); Log.d("CicloVida", "$nombre -> onResume") }
    override fun onPause() { super.onPause(); Log.d("CicloVida", "$nombre -> onPause") }
    override fun onStop() { super.onStop(); Log.d("CicloVida", "$nombre -> onStop") }
    override fun onDestroy() { super.onDestroy(); Log.d("CicloVida", "$nombre -> onDestroy") }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
