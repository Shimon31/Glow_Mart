package com.bcsbattle.sbs_e_mart.data

data class CartItem(
    val name: String?,
    val price: String,
    val category: String,
    var quantity: Int,
    val imageLink: String?

)
