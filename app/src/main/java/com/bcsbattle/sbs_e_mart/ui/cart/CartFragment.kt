package com.bcsbattle.sbs_e_mart.ui.cart

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bcsbattle.sbs_e_mart.base.BaseFragment
import com.bcsbattle.sbs_e_mart.databinding.FragmentCartBinding
import com.bcsbattle.sbs_e_mart.utils.Cart

class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {

    private lateinit var cartAdapter: CartAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load saved cart
        Cart.loadCart(requireContext())

        // Initialize the RecyclerView and Adapter
        cartAdapter = CartAdapter(Cart.getItems()) {
            // Callback to update total price
            updateTotalPrice()
        }
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
        _binding = null
    }
}
