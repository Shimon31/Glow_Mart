package com.bcsbattle.sbs_e_mart.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bcsbattle.sbs_e_mart.data.CartItem
import com.bcsbattle.sbs_e_mart.databinding.ItemCartBindingBinding

class CartAdapter(private val items: List<CartItem>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBindingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class CartViewHolder(private val binding: ItemCartBindingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.productName.text = item.name
            binding.productPrice.text = item.price
            binding.productQuantity.text = "Quantity: ${item.quantity}"
            binding.productImage.load(item.imageLink) // Load image using Coil
        }
    }
}
