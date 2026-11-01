package com.refreshpro.lavanderia.ui.pedidos

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.refreshpro.lavanderia.databinding.ItemPedidoBinding
import com.refreshpro.lavanderia.domain.model.Pedido
import com.refreshpro.lavanderia.utils.colorRes

/** ListAdapter + DiffUtil: solo se redibujan los ítems que cambian. */
class PedidoAdapter(
    private val onClick: (Pedido) -> Unit
) : ListAdapter<Pedido, PedidoAdapter.PedidoViewHolder>(DiffCallback) {

    class PedidoViewHolder(val binding: ItemPedidoBinding) : RecyclerView.ViewHolder(binding.root)

    private object DiffCallback : DiffUtil.ItemCallback<Pedido>() {
        override fun areItemsTheSame(a: Pedido, b: Pedido) = a.id == b.id
        override fun areContentsTheSame(a: Pedido, b: Pedido) = a == b
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val binding = ItemPedidoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PedidoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = getItem(position)
        with(holder.binding) {
            tvCliente.text = pedido.clienteNombre
            tvServicio.text = pedido.servicio
            tvFechaPrendas.text = "Recojo: ${pedido.fechaRecojo}  •  ${pedido.cantidadPrendas} prendas"
            tvDireccion.text = pedido.puntoRecojo
            tvEstado.text = pedido.estadoPedido.etiqueta
            val color = ContextCompat.getColor(root.context, pedido.estadoPedido.colorRes())
            tvEstado.backgroundTintList = ColorStateList.valueOf(color)
            cardPedido.setOnClickListener { onClick(pedido) }
        }
    }
}
