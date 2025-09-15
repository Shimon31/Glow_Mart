package com.bcsbattle.sbs_e_mart.ui.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bcsbattle.sbs_e_mart.data.model.message.Message

class MessageViewModel : ViewModel() {

    private val _messages = MutableLiveData<MutableList<Message>>(mutableListOf())
    val messages: LiveData<MutableList<Message>> get() = _messages

    fun addMessage(message: Message) {
        _messages.value?.add(message)
        _messages.value = _messages.value // Trigger LiveData observer
    }
}
