package com.bcsbattle.sbs_e_mart.ui.HomeFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bcsbattle.sbs_e_mart.data.Product.ResponseProduct
import com.bcsbattle.sbs_e_mart.databinding.ProductItemBinding

class HomeAdapter(private val listener: Listener) :
    ListAdapter<ResponseProduct, HomeAdapter.HomeViewModel>(comparator) {

    interface Listener {
        fun onItemClick(id: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeViewModel {
        return HomeViewModel(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: HomeViewModel,
        position: Int
    ) {
        getItem(position).let {

            holder.binding.apply {

                productImage.load(it.imageLink)
                productName.text = it.name
                productBrand.text = it.brand
                productPrice.text = "$${it.price}"
                productRating.text = "${it.rating}"
                productCategory.text = it.category


                holder.itemView.setOnClickListener {
                    listener.onItemClick(it.id)
                }


            }

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

    class HomeViewModel(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

}