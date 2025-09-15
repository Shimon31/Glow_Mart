package com.bcsbattle.sbs_e_mart.data.repo
import com.bcsbattle.sbs_e_mart.data.Service.ProductService
import com.bcsbattle.sbs_e_mart.data.model.Product.ResponseProduct
import retrofit2.Response
import javax.inject.Inject

class getProductRepo @Inject constructor(private val service : ProductService) {

    suspend fun getAllProducts() : Response<List<ResponseProduct>>{

        return service.getAllProducts()
    }

    suspend fun getProductById(id: Int) : Response<ResponseProduct>{
        return service.getProductById(id)
    }

    suspend fun getTrendyProduct(): Response<List<ResponseProduct>>{

        return service.getTrendyProducts()
    }

    suspend fun getAllProductForSearch(): Response<List<ResponseProduct>>{
        return  service.getAllProductsForSearch()
    }
}