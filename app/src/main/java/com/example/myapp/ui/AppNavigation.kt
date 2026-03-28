package com.example.myapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myapp.ui.screens.HomeScreen
import com.example.myapp.ui.screens.LoginScreen
import com.example.myapp.ui.screens.ProductDetailScreen
import com.example.myapp.ui.screens.RegisterScreen
import com.example.myapp.ui.screens.CartScreen
import com.example.myapp.ui.screens.ProfileScreen
import com.example.myapp.ui.screens.LandingScreen
import com.example.myapp.ui.screens.SearchScreen
import com.example.myapp.ui.screens.FavoritesScreen
import com.example.myapp.ui.screens.PaymentScreen



@Composable
fun AppNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Landing.route) {
        composable(Screen.Landing.route) {
            LandingScreen(
                onNavigateToLogin = { 
                    authViewModel.resetAuthState()
                    navController.navigate(Screen.Login.route) 
                }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onLoginSuccess = { 
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onRegisterSuccess = { navController.navigate(Screen.Login.route) }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = productViewModel,
                onNavigateToDetail = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onNavigateToCart = { navController.navigate(Screen.Cart.route) },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToSearch = { navController.navigate(Screen.Search.route) },
                onNavigateToFavorites = { navController.navigate(Screen.Favorites.route) }
            )
        }
        composable(Screen.Search.route) {
            SearchScreen(
                viewModel = productViewModel,
                onNavigateToDetail = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                viewModel = productViewModel,
                onNavigateToDetail = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = androidx.navigation.NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(
                productId = productId,
                viewModel = productViewModel,
                cartViewModel = cartViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCart = { navController.navigate(Screen.Cart.route) }
            )
        }
        composable(Screen.Cart.route) {
            CartScreen(
                viewModel = cartViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPayment = { navController.navigate(Screen.Payment.route) }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                viewModel = authViewModel,
                onNavigateBack = { navController.popBackStack() },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Payment.route) {
            PaymentScreen(
                cartViewModel = cartViewModel,
                onNavigateBack = { navController.popBackStack() },
                onPaymentSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

    }
}

