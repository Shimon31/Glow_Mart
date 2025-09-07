package com.bcsbattle.sbs_e_mart.ui.HomeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcsbattle.sbs_e_mart.data.Product.ResponseProduct
import com.bcsbattle.sbs_e_mart.repo.getProductRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo : getProductRepo) : ViewModel() {

    private var _allProductResponse = MutableLiveData<List<ResponseProduct>>()
    val allProductResponse : LiveData<List<ResponseProduct>>
        get() = _allProductResponse



    init {
        getAllProduct()
    }

    private fun getAllProduct(){

        viewModelScope.launch {

            val response = repo.getAllProducts()

            if (response.isSuccessful){
                _allProductResponse.postValue(response.body())
            }

        }
    }

    private var _productResponse = MutableLiveData<ResponseProduct>()
    val productResponse : LiveData<ResponseProduct>
        get() = _productResponse

    fun getProductById(id: Int){
        viewModelScope.launch {

            val repo = repo.getProductById(id)
        if (repo.isSuccessful){
            _productResponse.postValue(repo.body())
        }}

    }

}