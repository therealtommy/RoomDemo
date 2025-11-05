package com.example.roomdemo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val db = ProductRoomDatabase.getInstance(application)
    private val dao = db.productDao()
    val allProducts: LiveData<List<Product>> = dao.getAllProducts()

    fun addProduct(name: String, quantity: Int) {
        viewModelScope.launch {
            dao.insertProduct(Product(name, quantity))
        }
    }

    fun deleteProduct(name: String) {
        viewModelScope.launch {
            dao.deleteProduct(name)
        }
    }
    private val _searchResults = MutableLiveData<List<Product>>()
    val searchResults: LiveData<List<Product>> = _searchResults
    fun findProduct(name: String) {
        viewModelScope.launch {
            val results = dao.findProduct(name)
            _searchResults.value = results
        }
    }
}