package com.bcsbattle.sbs_e_mart.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.bcsbattle.sbs_e_mart.R
import com.bcsbattle.sbs_e_mart.base.BaseFragment
import com.bcsbattle.sbs_e_mart.databinding.FragmentDetailsBinding
import com.bcsbattle.sbs_e_mart.databinding.FragmentHomeBinding
import com.bcsbattle.sbs_e_mart.ui.HomeFragment.HomeViewModel


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.productResponse.observe(viewLifecycleOwner) {


            binding.apply {

                productImage.load(it.imageLink)
                productName.text = it.name
                productBrand.text = it.brand
                productPrice.text = "$${it.price}"
                productRating.text = "${it.rating}"
                productCategory.text = it.category
            }

        }
    }

}