package com.bcsbattle.sbs_e_mart.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcsbattle.sbs_e_mart.data.model.Product.ResponseProduct
import com.bcsbattle.sbs_e_mart.data.repo.getProductRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repo: getProductRepo) : ViewModel() {

    private var _productDetails = MutableLiveData<ResponseProduct?>()
    val productDetails: LiveData<ResponseProduct>
        get() = _productDetails as LiveData<ResponseProduct>

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getProductDetails(productId: Int) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val response = repo.getProductById(productId)
                if (response.isSuccessful) {
                    _productDetails.postValue(response.body())
                } else {
                    // Handle error case
                    _productDetails.postValue(null)
                }
            } catch (e: Exception) {
                // Handle network error
                _productDetails.postValue(null)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}