package com.bcsbattle.sbs_e_mart.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import coil.load
import com.bcsbattle.sbs_e_mart.base.BaseFragment
import com.bcsbattle.sbs_e_mart.databinding.FragmentDetailsBinding
import com.bcsbattle.sbs_e_mart.ui.HomeFragment.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    // Use viewModels() instead of activityViewModels()
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    productName.text = it.name
                    productBrand.text = it.brand
                    productPrice.text = "$${it.price}"
                    productRating.text = "${it.rating}"
                    productCategory.text = it.category
                    productDescription.text = it.description // Add this if you have description field
                }
            }
        }
    }
}