package com.bcsbattle.sbs_e_mart.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bcsbattle.sbs_e_mart.R
import com.bcsbattle.sbs_e_mart.base.BaseFragment
import com.bcsbattle.sbs_e_mart.databinding.FragmentCartBinding
import com.bcsbattle.sbs_e_mart.utils.Cart

class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    private lateinit var cartAdapter: CartAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the RecyclerView and Adapter
        cartAdapter = CartAdapter(Cart.getItems())
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = cartAdapter

        // Display the total price
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val total = Cart.getTotalPrice()
        binding.cartTotalPrice.text = "Total: $${String.format("%.2f", total)}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Nullify the binding to prevent memory leaks
        _binding = null
    }
}
