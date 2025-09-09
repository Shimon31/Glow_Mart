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
    HomeAdapter.Listener, HomeAdapter2.Listener {

    private val viewModel: HomeViewModel by viewModels()
    private val recommendedAdapter: HomeAdapter by lazy { HomeAdapter(this@HomeFragment) } // Adapter for recommended products
    private val trendyAdapter: HomeAdapter2 by lazy { HomeAdapter2(this@HomeFragment) }  // Adapter for trendy products

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up both RecyclerViews with their respective adapters
        setupRecyclerView1()
        setupRecyclerView2()

        // Observe the data from ViewModel and populate both RecyclerViews
        observeViewModel()
    }

    // Setup RecyclerView1 (Recommended Products)
    private fun setupRecyclerView1() = with(binding.recyclerView1) {
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        setHasFixedSize(true)
        adapter = recommendedAdapter  // Use recommendedAdapter for the first RecyclerView
    }

    // Setup RecyclerView2 (Trendy Products)
    private fun setupRecyclerView2() = with(binding.recyclerView2) {
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        setHasFixedSize(true)
        adapter = trendyAdapter  // Use trendyAdapter for the second RecyclerView
    }

    private fun observeViewModel() {
        // Observe loading state
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            if (loading) showProgressBar() else hideProgressBar()
        }

        // Observe recommended products (allProductResponse) for first RecyclerView
        viewModel.allProductResponse.observe(viewLifecycleOwner) { products ->
            if (!products.isNullOrEmpty()) {
                recommendedAdapter.submitList(products)  // Submit recommended products to the first RecyclerView
                showRecyclerView1()
            } else {
                Log.d("HomeFragment", "No recommended products received")
            }
        }

        // Observe trendy products (allTrendyProductResponse) for second RecyclerView
        viewModel.allTrendyProductResponse.observe(viewLifecycleOwner) { trendyProducts ->
            if (!trendyProducts.isNullOrEmpty()) {
                trendyAdapter.submitList(trendyProducts)  // Submit trendy products to the second RecyclerView
                showRecyclerView2()
            } else {
                Log.d("HomeFragment", "No trendy products received")
            }
        }
    }

    // Show progress bar while loading data
    private fun showProgressBar() = with(binding) {
        progressBar.visibility = View.VISIBLE
        recyclerView1.visibility = View.INVISIBLE
        recyclerView2.visibility = View.INVISIBLE
    }

    // Hide progress bar after data is loaded
    private fun hideProgressBar() = with(binding) {
        progressBar.visibility = View.GONE
        recyclerView1.visibility = View.VISIBLE
        recyclerView2.visibility = View.VISIBLE
    }

    // Show RecyclerView1 (Recommended Products)
    private fun showRecyclerView1() {
        binding.recyclerView1.visibility = View.VISIBLE
    }

    // Show RecyclerView2 (Trendy Products)
    private fun showRecyclerView2() {
        binding.recyclerView2.visibility = View.VISIBLE
    }

    // Item click handler for first RecyclerView (Recommended Products)
    override fun onItemClick(id: Int?) {
        Log.d("HomeFragment", "Product clicked: $id")
        val bundle = bundleOf("product_id" to id)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
    }

    // Item click handler for second RecyclerView (Trendy Products)
    fun onItemClick2(id: Int?) {
        Log.d("HomeFragment", "Trending Product clicked: $id")
        val bundle = bundleOf("product_id" to id)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
    }
}
