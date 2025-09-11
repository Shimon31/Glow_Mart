package com.bcsbattle.sbs_e_mart.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bcsbattle.sbs_e_mart.R
import com.bcsbattle.sbs_e_mart.base.BaseFragment
import com.bcsbattle.sbs_e_mart.data.Product.ResponseProduct
import com.bcsbattle.sbs_e_mart.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    SearchAdapter.Listener {

    private val viewModel: SearchViewModel by viewModels()
    private val adapter: SearchAdapter by lazy { SearchAdapter(this) }

    private var allProducts: List<ResponseProduct> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView1.adapter = adapter

        // observe loading state
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            if (loading) showProgressBar() else hideProgressBar()
        }

        // observe products
        viewModel.allSearchProducts.observe(viewLifecycleOwner) { products ->
            allProducts = products ?: emptyList()
            adapter.submitList(allProducts) // initially show all
        }

        // setup search
        binding.searchBar.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    }

    private fun filterList(query: String?) {
        val q = query?.trim().orEmpty()
        val filtered = if (q.isNotEmpty()) {
            allProducts.filter { product ->
                product.name?.contains(q, ignoreCase = true) ?: false
            }
        } else {
            allProducts
        }
        adapter.submitList(filtered)
    }

    private fun showProgressBar() = with(binding) {
        progressBar.visibility = View.VISIBLE
        recyclerView1.visibility = View.INVISIBLE

    }

    // Hide progress bar after data is loaded
    private fun hideProgressBar() = with(binding) {
        progressBar.visibility = View.GONE
        recyclerView1.visibility = View.VISIBLE

    }
    override fun onItemClick(id: Int?) {
        // handle navigation to details
    }
}
