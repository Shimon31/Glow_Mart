package com.bcsbattle.sbs_e_mart.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bcsbattle.sbs_e_mart.data.model.Product.ResponseProduct
import com.bcsbattle.sbs_e_mart.databinding.AllProductItemBinding

class SearchAdapter(val listener: Listener) : ListAdapter<ResponseProduct, SearchAdapter.SearchViewHolder>(comparator) {

    interface Listener {
        fun onItemClick(id: Int?)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder {
        return SearchViewHolder(
            AllProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: SearchViewHolder,
        position: Int
    ) {

        val product = getItem(position)

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
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResponseProduct,
                newItem: ResponseProduct
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    class SearchViewHolder(val binding: AllProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}