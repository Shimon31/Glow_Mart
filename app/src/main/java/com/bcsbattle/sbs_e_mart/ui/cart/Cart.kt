package com.bcsbattle.sbs_e_mart.utils

import android.content.Context
import com.bcsbattle.sbs_e_mart.data.CartItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Cart {
    private val items: MutableList<CartItem> = mutableListOf()

    // --- Add Item ---
    fun addItem(item: CartItem, context: Context) {
        val existingItem = items.find { it.name == item.name }
        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            items.add(item)
        }
        saveCart(context)
    }

    // --- Remove Item ---
    fun removeItem(item: CartItem, context: Context) {
        items.remove(item)
        saveCart(context)
    }

    // --- Get Items ---
    fun getItems(): MutableList<CartItem> = items

    // --- Total Price ---
    fun getTotalPrice(): Double {
        return items.sumOf { it.price.replace("$", "").toDoubleOrNull() ?: 0.0 * it.quantity }
    }

    // --- Save Cart in SharedPreferences ---
    private fun saveCart(context: Context) {
        val prefs = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE)
        val json = Gson().toJson(items)
        prefs.edit().putString("cart_items", json).apply()
    }

    // --- Load Cart from SharedPreferences ---
    fun loadCart(context: Context) {
        val prefs = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE)
        val json = prefs.getString("cart_items", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<CartItem>>() {}.type
            val savedItems: MutableList<CartItem> = Gson().fromJson(json, type)
            items.clear()
            items.addAll(savedItems)
        }
    }

    // --- Clear Cart ---
    fun clearCart(context: Context) {
        items.clear()
        saveCart(context)
    }
}
