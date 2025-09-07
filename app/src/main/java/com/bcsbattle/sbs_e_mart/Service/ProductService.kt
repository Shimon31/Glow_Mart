package com.bcsbattle.sbs_e_mart.Service

import com.bcsbattle.sbs_e_mart.data.Product.ResponseProduct
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {


    @GET("products.json?")
    suspend fun getAllProducts(): Response<List<ResponseProduct>>

    @GET("products.json?")
    suspend fun getProductById(

        @Path("id") id : Int

    ): Response<ResponseProduct>


}