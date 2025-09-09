package com.bcsbattle.sbs_e_mart.ui.HomeFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bcsbattle.sbs_e_mart.R
import com.bcsbattle.sbs_e_mart.base.BaseFragment
import com.bcsbattle.sbs_e_mart.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    HomeAdapter.Listener {

    private val viewModel: HomeViewModel by viewModels()

    private val adapter: HomeAdapter by lazy { HomeAdapter(this@HomeFragment) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() = with(binding.recyclerView1) {
        layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        setHasFixedSize(true)
        adapter = this@HomeFragment.adapter
        // If inside NestedScrollView:
        // isNestedScrollingEnabled = false
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            if (loading) showProgressBar() else hideProgressBar()
        }

        viewModel.allProductResponse.observe(viewLifecycleOwner) { products ->
            if (!products.isNullOrEmpty()) {
                adapter.submitList(products)
                showRecyclerView()
            } else {
                Log.d("HomeFragment", "No products received")
            }
        }
    }

    private fun showProgressBar() = with(binding) {
        progressBar.visibility = View.VISIBLE
        recyclerView1.visibility = View.INVISIBLE // keep layout size; spinner overlays
    }

    private fun hideProgressBar() = with(binding) {
        progressBar.visibility = View.GONE
        recyclerView1.visibility = View.VISIBLE
    }

    private fun showRecyclerView() {
        binding.recyclerView1.visibility = View.VISIBLE
    }

    override fun onItemClick(id: Int?) {
        Log.d("HomeFragment", "Product clicked: $id")
        val bundle = bundleOf("product_id" to id)
        findNavController().navigate(
            R.id.action_homeFragment_to_detailsFragment,
            bundle
        )
    }
}
