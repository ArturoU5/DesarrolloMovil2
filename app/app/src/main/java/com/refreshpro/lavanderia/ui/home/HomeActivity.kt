package com.refreshpro.lavanderia.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.refreshpro.lavanderia.R
import com.refreshpro.lavanderia.data.repository.AuthRepository
import com.refreshpro.lavanderia.databinding.ActivityHomeBinding
import com.refreshpro.lavanderia.ui.BaseActivity
import com.refreshpro.lavanderia.ui.admin.AdminFragment
import com.refreshpro.lavanderia.ui.contacto.ContactosFragment
import com.refreshpro.lavanderia.ui.contacto.NosotrosFragment
import com.refreshpro.lavanderia.ui.login.LoginActivity
import com.refreshpro.lavanderia.ui.mapa.MapaActivity
import com.refreshpro.lavanderia.ui.pedidos.PedidosFragment
import com.refreshpro.lavanderia.ui.servicios.ServiciosFragment
import com.refreshpro.lavanderia.utils.Extras

/** Pantalla principal: Navigation Drawer que muestra Fragments. */
class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Datos del usuario en la cabecera del menú
        val cabecera = binding.navView.getHeaderView(0)
        cabecera.findViewById<TextView>(R.id.tvNavNombre).text = nombreUsuario()
        cabecera.findViewById<TextView>(R.id.tvNavCorreo).text =
            AuthRepository.usuarioActual?.correo ?: ""
        cabecera.findViewById<TextView>(R.id.tvNavRol).text =
            AuthRepository.usuarioActual?.rol?.etiqueta ?: ""

        // Control basado en roles: la opción de administración solo la ve el ADMIN
        binding.navView.menu.findItem(R.id.nav_admin).isVisible = AuthRepository.esAdmin()

        binding.navView.setNavigationItemSelectedListener { item ->
            abrirDestino(item.itemId)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // El botón atrás cierra el menú si está abierto
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })

        if (savedInstanceState == null) irA(R.id.nav_inicio)
    }

    private fun nombreUsuario(): String =
        intent.getStringExtra(Extras.NOMBRE_USUARIO) ?: "usuario"

    /** Lo usan los Fragments para navegar a otra opción del menú. */
    fun irA(itemId: Int) {
        binding.navView.setCheckedItem(itemId)
        abrirDestino(itemId)
    }

    private fun abrirDestino(itemId: Int) {
        when (itemId) {
            R.id.nav_inicio -> mostrar(
                HomeFragment().apply {
                    // Envío de datos Activity -> Fragment
                    arguments = bundleOf(Extras.NOMBRE_USUARIO to nombreUsuario())
                }
            )
            R.id.nav_admin -> if (AuthRepository.esAdmin()) mostrar(AdminFragment())
            R.id.nav_pedidos -> mostrar(PedidosFragment())
            R.id.nav_servicios -> mostrar(ServiciosFragment())
            R.id.nav_contactos -> mostrar(ContactosFragment())
            R.id.nav_nosotros -> mostrar(NosotrosFragment())
            R.id.nav_mapa -> startActivity(Intent(this, MapaActivity::class.java))
            R.id.nav_logout -> cerrarSesion()
        }
    }

    private fun mostrar(fragmento: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor, fragmento)
            .commit()
    }

    private fun cerrarSesion() {
        AuthRepository.cerrarSesion()
        startActivity(
            Intent(this, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    }
}
