package com.bcsbattle.sbs_e_mart.utils

import com.bcsbattle.sbs_e_mart.data.CartItem

object Cart {
    private val items: MutableList<CartItem> = mutableListOf()

    fun addItem(item: CartItem) {
        val existingItem = items.find { it.name == item.name }
        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            items.add(item)
        }
    }

    fun getItems(): MutableList<CartItem> = items

    fun getTotalPrice(): Double {
        return items.sumOf { it.price.replace("$", "").toDoubleOrNull() ?: 0.0 * it.quantity }
    }
}
