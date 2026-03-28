package com.example.myapp.ui

sealed class Screen(val route: String) {
    object Landing : Screen("landing")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object ProductDetail : Screen("detail/{productId}") {
        fun createRoute(productId: Int) = "detail/$productId"
    }
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object Search : Screen("search")
    object Favorites : Screen("favorites")
    object Payment : Screen("payment")
}


