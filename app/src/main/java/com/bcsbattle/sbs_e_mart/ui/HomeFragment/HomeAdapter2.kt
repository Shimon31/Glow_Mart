package com.bcsbattle.sbs_e_mart.ui.HomeFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bcsbattle.sbs_e_mart.data.model.Product.ResponseProduct
import com.bcsbattle.sbs_e_mart.databinding.ProductItem2Binding

class HomeAdapter2(private val listener: Listener) :
    ListAdapter<ResponseProduct, HomeAdapter2.HomeViewModel2>(comparator) {

    interface Listener {
        fun onItemClick(id: Int?)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeViewModel2 {
        return HomeViewModel2(
            ProductItem2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: HomeViewModel2,
        position: Int
    ) {
        val product = getItem(position) // Get the product first
        Log.d("RCV2", "onBindViewHolder: $product")
        holder.binding.apply {
            productImage.load(product.imageLink)
            productName.text = product.name
            productBrand.text = product.brand
            productPrice.text = "$${product.price}"
            productRating.text = "${product.rating}"
            productCategory.text = product.category
        }

        // Set click listener outside the binding scope
        holder.itemView.setOnClickListener {
            listener.onItemClick(product.id) // Use the correct product ID
        }
    }

    companion object {
        val comparator = object : DiffUtil.ItemCallback<ResponseProduct>() {
            override fun areItemsTheSame(
                oldItem: ResponseProduct,
                newItem: ResponseProduct
            ): Boolean {
                return oldItem.id == newItem.id // Compare by ID, not entire object
            }

            override fun areContentsTheSame(
                oldItem: ResponseProduct,
                newItem: ResponseProduct
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class HomeViewModel2(val binding: ProductItem2Binding) : RecyclerView.ViewHolder(binding.root)
}