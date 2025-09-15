package com.bcsbattle.sbs_e_mart.ui.message

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bcsbattle.sbs_e_mart.base.BaseFragment
import com.bcsbattle.sbs_e_mart.data.model.message.Message
import com.bcsbattle.sbs_e_mart.databinding.FragmentMessageBinding

class MessageFragment : BaseFragment<FragmentMessageBinding>(FragmentMessageBinding::inflate) {

    private val messages = mutableListOf<Message>()
    private lateinit var adapter: MessageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MessageAdapter(messages)
        binding.messageRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.messageRecyclerView.adapter = adapter

        // initial buyer messages
        messages.add(Message("Hello! I am interested in this product.", false))
        messages.add(Message("Can you tell me more about it?", false))
        adapter.notifyDataSetChanged()
        binding.messageRecyclerView.scrollToPosition(messages.size - 1)

        // send button
        binding.sendButton.setOnClickListener {
            val text = binding.messageEditText.text.toString().trim()
            if (text.isNotEmpty()) {
                val message = Message(text, isSeller = true)
                messages.add(message)
                adapter.notifyItemInserted(messages.size - 1)
                binding.messageRecyclerView.scrollToPosition(messages.size - 1)
                binding.messageEditText.text?.clear()
            }
        }
    }
}
