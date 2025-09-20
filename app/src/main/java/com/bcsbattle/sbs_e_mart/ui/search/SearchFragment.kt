package com.bcsbattle.sbs_e_mart.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bcsbattle.sbs_e_mart.R
import com.bcsbattle.sbs_e_mart.core.base.BaseFragment
import com.bcsbattle.sbs_e_mart.data.model.Product.ResponseProduct
import com.bcsbattle.sbs_e_mart.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    SearchAdapter.Listener {

    private val viewModel: SearchViewModel by viewModels()
    private val adapter: SearchAdapter by lazy { SearchAdapter(this) }

    private var allProducts: List<ResponseProduct> = emptyList()
    private var filteredProducts: List<ResponseProduct> = emptyList()

    // Track selected filters
    private val selectedBrands = mutableSetOf<String>()
    private val selectedCategories = mutableSetOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView1.adapter = adapter

        // Observe loading state
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            if (loading) showProgressBar() else hideProgressBar()
        }

        // Observe products
        viewModel.allSearchProducts.observe(viewLifecycleOwner) { products ->
            allProducts = products ?: emptyList()
            filteredProducts = allProducts
            adapter.submitList(filteredProducts)
        }

        // Setup search bar
        binding.searchBar.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterList(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        // Setup filter button
        binding.filterButton.setOnClickListener {
            showFilterDialog()
        }
    }

    private fun filterList(query: String?) {
        val q = query?.trim().orEmpty()
        filteredProducts = allProducts.filter { product ->
            val matchesQuery = product.name?.contains(q, ignoreCase = true) ?: false
            val matchesBrand = selectedBrands.isEmpty() || selectedBrands.contains(product.brand)
            val matchesCategory = selectedCategories.isEmpty() || selectedCategories.contains(product.category)
            matchesQuery && matchesBrand && matchesCategory
        }
        adapter.submitList(filteredProducts)
    }

    private fun showFilterDialog() {
        val brands = allProducts.mapNotNull { it.brand }.distinct().toTypedArray()
        val categories = allProducts.mapNotNull { it.category }.distinct().toTypedArray()

        // Temporary selections
        val selectedBrandArray = BooleanArray(brands.size) { selectedBrands.contains(brands[it]) }
        val selectedCategoryArray = BooleanArray(categories.size) { selectedCategories.contains(categories[it]) }

        // Show multi-choice dialog for brand
        AlertDialog.Builder(requireContext())
            .setTitle("Select Brands")
            .setMultiChoiceItems(brands, selectedBrandArray) { _, which, isChecked ->
                selectedBrandArray[which] = isChecked
            }
            .setPositiveButton("Next") { _, _ ->
                selectedBrands.clear()
                brands.forEachIndexed { index, brand ->
                    if (selectedBrandArray[index]) selectedBrands.add(brand)
                }

                // Show multi-choice dialog for category
                AlertDialog.Builder(requireContext())
                    .setTitle("Select Categories")
                    .setMultiChoiceItems(categories, selectedCategoryArray) { _, which, isChecked ->
                        selectedCategoryArray[which] = isChecked
                    }
                    .setPositiveButton("Apply") { _, _ ->
                        selectedCategories.clear()
                        categories.forEachIndexed { index, cat ->
                            if (selectedCategoryArray[index]) selectedCategories.add(cat)
                        }
                        // Apply filters
                        filterList(binding.searchBar.query.toString())
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showProgressBar() = with(binding) {
        progressBar.visibility = View.VISIBLE
        recyclerView1.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() = with(binding) {
        progressBar.visibility = View.GONE
        recyclerView1.visibility = View.VISIBLE
    }

    override fun onItemClick(id: Int?) {
        val bundle = bundleOf("product_id" to id)
        findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, bundle)
    }
}
