package com.bcsbattle.sbs_e_mart.ui.HomeFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.bcsbattle.sbs_e_mart.R
import com.bcsbattle.sbs_e_mart.base.BaseFragment
import com.bcsbattle.sbs_e_mart.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


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

          it.forEach { item->

              Log.d("TAG", "onViewCreated: ${item}")


          }

      }



    }

    override fun onItemClick(id: Int) {
       viewModel.getProductById(id)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
    }
}