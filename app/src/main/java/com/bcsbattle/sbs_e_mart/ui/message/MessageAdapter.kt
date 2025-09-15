package com.bcsbattle.sbs_e_mart.ui.message

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bcsbattle.sbs_e_mart.R
import com.bcsbattle.sbs_e_mart.data.model.message.Message
import com.bcsbattle.sbs_e_mart.databinding.ItemMessageBinding

class MessageAdapter(private var messages: List<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    fun submitList(newList: List<Message>) {
        messages = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.binding.apply {
            messageText.text = message.text

            // Align message to left/right
            val params = messageText.layoutParams as LinearLayout.LayoutParams
            params.gravity = if (message.isSeller) Gravity.END else Gravity.START
            messageText.layoutParams = params

            // Change background for buyer/seller
            messageText.setBackgroundResource(
                if (message.isSeller) R.drawable.bg_seller_message else R.drawable.bg_buyer_message
            )
        }
    }

    class MessageViewHolder(val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root)
}