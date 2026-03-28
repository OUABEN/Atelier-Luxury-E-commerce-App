package com.example.myapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.R
import com.example.myapp.data.CartItem
import com.example.myapp.ui.CartViewModel
import com.example.myapp.ui.theme.*
import com.example.myapp.ui.utils.getProductImageRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: CartViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToPayment: () -> Unit
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice = viewModel.getTotalPrice()

    Box(modifier = Modifier.fillMaxSize().background(AtelierBackground)) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "PANIER",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = AtelierPrimary,
                                letterSpacing = 6.sp,
                                fontWeight = FontWeight.Light
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        if (cartItems.isNotEmpty()) {
                            IconButton(onClick = { viewModel.clearCart() }) {
                                Icon(Icons.Default.Delete, contentDescription = "Clear", tint = Color.White.copy(alpha = 0.6f))
                            }
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.statusBarsPadding()
                )
            },
            containerColor = Color.Transparent
        ) { padding ->
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.White.copy(alpha = 0.2f)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            "Votre panier est vide",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Light,
                                letterSpacing = 1.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        OutlinedButton(
                            onClick = onNavigateBack,
                            modifier = Modifier.height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, AtelierPrimary.copy(alpha = 0.5f)),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = AtelierPrimary)
                        ) {
                            Text("Continuer le shopping", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            } else {
                Column(modifier = Modifier.padding(padding).fillMaxSize()) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(cartItems) { item ->
                            CartItemRow(item, viewModel)
                        }
                    }

                    // Bottom Order Summary
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                            .border(0.5.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
                        color = Color(0xFF0F0F0F).copy(alpha = 0.95f) // Deep opaque color to match BottomBar of HomeScreen
                    ) {
                        Column(
                            modifier = Modifier
                                .navigationBarsPadding()
                                .padding(32.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Total",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        color = Color.White.copy(alpha = 0.7f),
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                                Text(
                                    "€${String.format("%,d", totalPrice.toInt())}",
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        color = AtelierPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Button(
                                onClick = onNavigateToPayment,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AtelierPrimary,
                                    contentColor = Color.Black
                                )
                            ) {
                                Text("Finaliser la commande", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(item: CartItem, viewModel: CartViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .border(0.5.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(24.dp)),
        color = AtelierSurface.copy(alpha = 0.4f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image Glass Box
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.03f))
                    .border(0.5.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                val imageRes = getProductImageRes(item.image)
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(0.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "€${String.format("%,d", item.price.toInt())}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = AtelierPrimary,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Elegant Quantity Controls
            Surface(
                shape = RoundedCornerShape(100.dp), // Pill shape
                color = Color.Black.copy(alpha = 0.3f),
                border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.1f))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .clickable { viewModel.decrementQuantity(item.productId) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("-", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                    }

                    Text(
                        text = item.quantity.toString(),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(AtelierPrimary)
                            .clickable { viewModel.incrementQuantity(item.productId) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.Black,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
