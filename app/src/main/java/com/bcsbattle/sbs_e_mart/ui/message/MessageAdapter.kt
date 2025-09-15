package com.bcsbattle.sbs_e_mart.ui.message

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bcsbattle.sbs_e_mart.R
import com.bcsbattle.sbs_e_mart.data.model.message.Message
import com.bcsbattle.sbs_e_mart.databinding.ItemMessageBinding

class MessageAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.binding.messageText.text = message.text

        val params = holder.binding.messageText.layoutParams as LinearLayout.LayoutParams
        if (message.isSeller) {
            params.gravity = Gravity.END
            holder.binding.messageText.setBackgroundResource(R.drawable.bg_seller_message)
            holder.binding.messageText.setTextColor(holder.binding.root.context.getColor(android.R.color.white))
        } else {
            params.gravity = Gravity.START
            holder.binding.messageText.setBackgroundResource(R.drawable.bg_buyer_message)
            holder.binding.messageText.setTextColor(holder.binding.root.context.getColor(android.R.color.black))
        }
        holder.binding.messageText.layoutParams = params
    }

    override fun getItemCount(): Int = messages.size
}
