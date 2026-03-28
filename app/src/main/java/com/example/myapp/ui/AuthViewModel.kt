package com.example.myapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.data.Repository
import com.example.myapp.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(private val repository: Repository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    fun register(name: String, email: String, pass: String) {
        if (name.isBlank() || email.isBlank() || pass.isBlank()) {
            _authState.value = AuthState.Error("Tous les champs sont obligatoires")
            return
        }
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val existing = repository.getUserByEmail(email)
            if (existing != null) {
                _authState.value = AuthState.Error("Email already registered")
            } else {
                repository.registerUser(User(name = name, email = email, password = pass))
                _authState.value = AuthState.Success
            }
        }
    }

    fun login(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _authState.value = AuthState.Error("Veuillez saisir votre email et mot de passe")
            return
        }
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = repository.loginUser(email, pass)
            if (user != null) {
                _currentUser.value = user
                _authState.value = AuthState.Success
            } else {
                _authState.value = AuthState.Error("Invalid email or password")
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _authState.value = AuthState.Idle
    }

    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }

}
