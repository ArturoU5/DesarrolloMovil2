package com.refreshpro.lavanderia.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.refreshpro.lavanderia.databinding.ActivitySplashBinding
import com.refreshpro.lavanderia.ui.BaseActivity
import com.refreshpro.lavanderia.ui.login.LoginActivity

class SplashActivity : BaseActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySplashBinding.inflate(layoutInflater).root)

        handler.postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2000)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null) // evita fugas si se cierra antes
        super.onDestroy()
    }
}
