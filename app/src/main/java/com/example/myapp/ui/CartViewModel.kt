package com.example.myapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.data.CartItem
import com.example.myapp.data.Product
import com.example.myapp.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val repository: Repository) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getCartItems().collect {
                _cartItems.value = it
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            repository.addToCart(product)
        }
    }

    fun removeFromCart(id: Int) {
        viewModelScope.launch {
            repository.removeFromCart(id)
        }
    }

    fun incrementQuantity(productId: Int) {
        viewModelScope.launch {
            repository.incrementQuantity(productId)
        }
    }

    fun decrementQuantity(productId: Int) {
        viewModelScope.launch {
            repository.decrementQuantity(productId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }

    fun getTotalPrice(): Double {
        return _cartItems.value.sumOf { it.price * it.quantity }
    }
}
