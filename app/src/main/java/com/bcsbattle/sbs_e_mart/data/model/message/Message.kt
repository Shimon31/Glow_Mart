package com.bcsbattle.sbs_e_mart.data.model.message

// Data class for a message
data class Message(
    val text: String,
    val isSeller: Boolean // true if sent by seller, false if sent by buyer
)
