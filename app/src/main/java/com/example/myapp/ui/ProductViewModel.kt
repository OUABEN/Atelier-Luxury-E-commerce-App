package com.example.myapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.data.Product
import com.example.myapp.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: Repository) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    open val products: StateFlow<List<Product>> = _products

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct

    private val _favorites = MutableStateFlow<List<Product>>(emptyList())
    val favorites: StateFlow<List<Product>> = _favorites

    init {
        viewModelScope.launch {
            repository.populateData()
            loadProducts()
            loadFavorites()
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = repository.getFavorites()
        }
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            repository.updateFavoriteStatus(product.id, !product.isFavorite)
            loadProducts()
            loadFavorites()
        }
    }

    fun loadProducts() {
        viewModelScope.launch {
            _products.value = repository.getAllProducts()
        }
    }

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }

    fun filterByCategory(category: String) {
        viewModelScope.launch {
            _products.value = repository.getProductsByCategory(category)
        }
    }
}
