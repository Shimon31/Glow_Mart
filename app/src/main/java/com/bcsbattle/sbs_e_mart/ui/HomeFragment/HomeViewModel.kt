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

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    init {
        getAllProduct()
    }

    private fun getAllProduct(){
        viewModelScope.launch {
            _isLoading.postValue(true)
            val response = repo.getAllProducts()
            if (response.isSuccessful){
                _allProductResponse.postValue(response.body())
            }
            _isLoading.postValue(false)
        }
    }

    private var _productResponse = MutableLiveData<ResponseProduct?>()
    val productResponse : LiveData<ResponseProduct>
        get() = _productResponse as LiveData<ResponseProduct>

    private var _isLoadingDetails = MutableLiveData<Boolean>()
    val isLoadingDetails : LiveData<Boolean>
        get() = _isLoadingDetails

    fun getProductById(id: Int){
        viewModelScope.launch {
            _isLoadingDetails.postValue(true)
            val response = repo.getProductById(id)
            if (response.isSuccessful){
                _productResponse.postValue(response.body())
            }
            _isLoadingDetails.postValue(false)
        }
    }

    // Clear product details when needed
    fun clearProductDetails() {
        _productResponse.postValue(null)
    }
}