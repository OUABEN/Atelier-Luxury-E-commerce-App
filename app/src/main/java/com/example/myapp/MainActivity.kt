package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.myapp.data.AppDatabase
import com.example.myapp.data.Repository
import com.example.myapp.ui.AppNavigation
import com.example.myapp.ui.AuthViewModel
import com.example.myapp.ui.CartViewModel
import com.example.myapp.ui.ProductViewModel
import com.example.myapp.ui.ViewModelFactory
import com.example.myapp.ui.theme.MyAPPTheme
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Data Layer
        val database = AppDatabase.getDatabase(this)
        val repository = Repository(database.userDao(), database.productDao(), database.cartDao())
        
        // Initialize ViewModels using Factory
        val factory = ViewModelFactory(repository)
        val authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        val productViewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]
        val cartViewModel = ViewModelProvider(this, factory)[CartViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            MyAPPTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    AppNavigation(
                        navController = navController,
                        authViewModel = authViewModel,
                        productViewModel = productViewModel,
                        cartViewModel = cartViewModel
                    )
                }
            }
        }

    }
}