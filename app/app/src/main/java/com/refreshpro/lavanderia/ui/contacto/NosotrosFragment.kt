package com.refreshpro.lavanderia.ui.contacto

import androidx.fragment.app.Fragment
import com.refreshpro.lavanderia.R
import com.refreshpro.lavanderia.utils.ponerTitulo

class NosotrosFragment : Fragment(R.layout.fragment_nosotros) {

    override fun onResume() {
        super.onResume()
        ponerTitulo("Nosotros")
    }
}
