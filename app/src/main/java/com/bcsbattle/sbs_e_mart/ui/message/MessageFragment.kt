package com.bcsbattle.sbs_e_mart.ui.message

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bcsbattle.sbs_e_mart.base.BaseFragment
import com.bcsbattle.sbs_e_mart.data.model.message.Message
import com.bcsbattle.sbs_e_mart.databinding.FragmentMessageBinding

class MessageFragment : BaseFragment<FragmentMessageBinding>(FragmentMessageBinding::inflate) {

    private lateinit var adapter: MessageAdapter
    private val viewModel: MessageViewModel by viewModels() // ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MessageAdapter(mutableListOf())
        binding.messageRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.messageRecyclerView.adapter = adapter

        // Observe messages from ViewModel
        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            adapter.submitList(messages.toList()) // Submit a copy of list
            binding.messageRecyclerView.scrollToPosition(messages.size - 1)
        }

        // Send button click
        binding.sendButton.setOnClickListener {
            val text = binding.messageEditText.text.toString().trim()
            if (text.isNotEmpty()) {
                val message = Message(text, isSeller = true)
                viewModel.addMessage(message)
                binding.messageEditText.text?.clear()
            }
        }

        // Example initial buyer messages (only add once)
        if (viewModel.messages.value.isNullOrEmpty()) {
            viewModel.addMessage(Message("Hello! I am interested in this product.", false))
            viewModel.addMessage(Message("Can you tell me more about it?", false))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
