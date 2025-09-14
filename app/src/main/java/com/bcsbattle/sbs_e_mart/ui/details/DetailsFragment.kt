package com.bcsbattle.sbs_e_mart.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.bcsbattle.sbs_e_mart.R
import com.bcsbattle.sbs_e_mart.base.BaseFragment
import com.bcsbattle.sbs_e_mart.databinding.FragmentDetailsBinding
import com.bcsbattle.sbs_e_mart.ui.HomeFragment.HomeViewModel
import com.bcsbattle.sbs_e_mart.utils.Cart
import com.bcsbattle.sbs_e_mart.data.CartItem
import com.bcsbattle.sbs_e_mart.data.Product.ResponseProduct
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Back button functionality
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_homeFragment)
        }

        // Get product ID from navigation arguments
        val productId = arguments?.getInt("product_id")
        Log.d("DetailsFragment", "Received product ID: $productId")

        if (productId == null) {
            return
        }

        // Call API to get product details
        viewModel.getProductById(productId)

        // Observe the response
        viewModel.productResponse.observe(viewLifecycleOwner) { product ->
            product?.let {
                binding.apply {
                    productImage.load(it.imageLink)
                    productName.text = "Product Name: ${it.name}"
                    productBrand.text = "Brand: ${it.brand}"
                    productPrice.text = "Price: $${it.price}"
                    productRating.text = "Rate: ${it.rating}"
                    productCategory.text = "Category: ${it.category}"
                    productDescription.text = "Description: ${it.description}"

                    // Add to Cart button functionality
                    addToCartTV.setOnClickListener {view ->
                        addToCart(it)
                    }
                }
            }
        }
    }

    private fun addToCart(product: ResponseProduct) {
        // Add the product to cart
        Cart.addItem(
            CartItem(
                product.name,
                product.price ?: "0.0",
                product.category ?: "Unknown",
                1,
                product.imageLink
            )
        )
        Toast.makeText(requireContext(), "${product.name} added to cart", Toast.LENGTH_SHORT).show()

        // Navigate to the CartFragment
        findNavController().navigate(R.id.action_detailsFragment_to_cartFragment3)
    }
}
