package com.bcsbattle.sbs_e_mart.data.Product


import com.google.gson.annotations.SerializedName

data class ProductColor(
    @SerializedName("colour_name")
    var colourName: String?,
    @SerializedName("hex_value")
    var hexValue: String?
)