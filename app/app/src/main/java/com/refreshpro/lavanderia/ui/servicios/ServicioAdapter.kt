package com.refreshpro.lavanderia.ui.servicios

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.refreshpro.lavanderia.databinding.ItemServicioBinding
import com.refreshpro.lavanderia.domain.model.Servicio
import java.util.Locale

class ServicioAdapter(
    private val onClick: (Servicio) -> Unit
) : ListAdapter<Servicio, ServicioAdapter.ServicioViewHolder>(DiffCallback) {

    class ServicioViewHolder(val binding: ItemServicioBinding) : RecyclerView.ViewHolder(binding.root)

    private object DiffCallback : DiffUtil.ItemCallback<Servicio>() {
        override fun areItemsTheSame(a: Servicio, b: Servicio) = a.id == b.id
        override fun areContentsTheSame(a: Servicio, b: Servicio) = a == b
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioViewHolder =
        ServicioViewHolder(ItemServicioBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ServicioViewHolder, position: Int) {
        val servicio = getItem(position)
        with(holder.binding) {
            tvNombre.text = servicio.nombre
            tvPrecio.text = String.format(Locale.US, "S/ %.2f %s", servicio.precio, servicio.unidad)
            cardServicio.setOnClickListener { onClick(servicio) }
        }
    }
}
