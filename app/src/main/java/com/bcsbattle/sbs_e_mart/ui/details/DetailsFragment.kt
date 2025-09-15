package com.bcsbattle.sbs_e_mart.ui.details

import android.os.Bundle
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
import com.bcsbattle.sbs_e_mart.data.model.Product.ResponseProduct
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        val productId = arguments?.getInt("product_id") ?: return

        viewModel.getProductById(productId)
        viewModel.productResponse.observe(viewLifecycleOwner) { product ->
            product?.let { bindProduct(it) }
        }
    }

    private fun bindProduct(product: ResponseProduct) {
        binding.apply {
            productImage.load(product.imageLink)
            productName.text = "Product Name: ${product.name}"
            productBrand.text = "Brand: ${product.brand}"
            productPrice.text = "Price: $${product.price}"
            productRating.text = "Rate: ${product.rating}"
            productCategory.text = "Category: ${product.category}"
            productDescription.text = "Description: ${product.description}"

            addToCartTV.setOnClickListener {
                Cart.addItem(
                    CartItem(
                        product.name,
                        product.price ?: "0.0",
                        product.category ?: "Unknown",
                        1,
                        product.imageLink
                    ),
                    requireContext() // important for persistence
                )
                Toast.makeText(requireContext(), "${product.name} added to cart", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_detailsFragment_to_cartFragment3)
            }
        }
    }
}
