package com.bcsbattle.sbs_e_mart.ui.search

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
class SearchViewModel @Inject constructor(
    private val repo: getProductRepo
) : ViewModel() {

    private val _allSearchProducts = MutableLiveData<List<ResponseProduct>>()
    val allSearchProducts: LiveData<List<ResponseProduct>> get() = _allSearchProducts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        getAllSearchProducts()
    }

    private fun getAllSearchProducts() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val response = repo.getAllProductForSearch()
            if (response.isSuccessful) {
                _allSearchProducts.postValue(response.body())
            } else {
                _allSearchProducts.postValue(emptyList())
            }
            _isLoading.postValue(false)
        }
    }
}
