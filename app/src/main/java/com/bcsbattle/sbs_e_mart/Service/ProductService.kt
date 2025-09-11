package com.bcsbattle.sbs_e_mart.Service

import com.bcsbattle.sbs_e_mart.data.Product.ResponseProduct
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {


    @GET("products.json?brand=maybelline")
    suspend fun getAllProducts(): Response<List<ResponseProduct>>

    // The @GET URL should include the {id} placeholder in the path
    @GET("products/{id}.json")  // Replace `products/{id}.json` with the actual API endpoint
    suspend fun getProductById(@Path("id") productId: Int): Response<ResponseProduct>


    @GET("products.json?brand=covergirl&product_type=mascara")
    suspend fun getTrendyProducts(): Response<List<ResponseProduct>>

    @GET("products.json?")
    suspend fun getAllProductsForSearch(): Response<List<ResponseProduct>>



}