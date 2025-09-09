package com.bcsbattle.sbs_e_mart.ui.HomeFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bcsbattle.sbs_e_mart.R
import com.bcsbattle.sbs_e_mart.base.BaseFragment
import com.bcsbattle.sbs_e_mart.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), HomeAdapter.Listener {

    val viewModel : HomeViewModel by viewModels()

    val adapter : HomeAdapter by lazy {
        HomeAdapter(this@HomeFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allProductResponse.observe(viewLifecycleOwner){
            adapter.submitList(it)
            binding.recyclerView1.adapter=adapter
        }
    }

    override fun onItemClick(id: Int?) {
        val bundle = bundleOf("product_id" to id)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
    }
}