package com.example.myapp.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.myapp.R
import com.example.myapp.ui.CartViewModel
import com.example.myapp.ui.ProductViewModel
import com.example.myapp.ui.theme.*
import com.example.myapp.ui.utils.getProductImageRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToCart: () -> Unit
) {
    val products by viewModel.products.collectAsState()
    val product = products.find { it.id == productId }
    val scrollState = rememberScrollState()

    if (product == null) {
        Box(modifier = Modifier.fillMaxSize().background(AtelierBackground), contentAlignment = Alignment.Center) {
            Text("Product not found", color = Color.White)
        }
        return
    }

    Box(modifier = Modifier.fillMaxSize().background(AtelierBackground)) {
        // Hero Image Background layer (Parallax effect)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(480.dp)
        ) {
            val imageRes = getProductImageRes(product.image)
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .offset { IntOffset(0, (scrollState.value * 0.5f).toInt()) },
                contentScale = ContentScale.Crop
            )

            // Gradient overlay to blend into the background seamlessly
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.6f), // Darker at top for TopBar contrast
                                Color.Black.copy(alpha = 0.2f),
                                AtelierBackground
                            )
                        )
                    )
            )
        }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "ATELIER",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = AtelierPrimary,
                                letterSpacing = 6.sp,
                                fontWeight = FontWeight.Light
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.toggleFavorite(product) }) {
                            Icon(
                                if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (product.isFavorite) AtelierPrimary else Color.White
                            )
                        }
                        IconButton(onClick = onNavigateToCart) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.White)
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                // Transparent spacer to reveal the parallax background image
                Spacer(modifier = Modifier.height(350.dp))

                // Content Area
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .background(AtelierBackground)
                        .padding(24.dp)
                ) {
                    // Category & Rating
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = AtelierPrimary.copy(alpha = 0.15f),
                            border = BorderStroke(0.5.dp, AtelierPrimary.copy(alpha = 0.3f))
                        ) {
                            Text(
                                "ÉDITION LIMITÉE",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = AtelierPrimary,
                                    letterSpacing = 2.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = "Rating", tint = AtelierPrimary, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("4.9", fontWeight = FontWeight.Bold, color = Color.White)
                            Text(" (128)", color = Color.White.copy(alpha = 0.6f))
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Title & Price
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            product.name,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(Modifier.width(16.dp))
                        Text(
                            "€${String.format("%,d", product.price.toInt())}",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = AtelierPrimary,
                                fontWeight = FontWeight.Light
                            )
                        )
                    }

                    Spacer(Modifier.height(32.dp))

                    // Specs (Glassmorphic)
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        SpecCard(
                            label = "MOUVEMENT",
                            value = if (product.category == "Watch") "Caliber 321" else "Eau de Parfum",
                            modifier = Modifier.weight(1f)
                        )
                        SpecCard(
                            label = "RÉSERVE",
                            value = if (product.category == "Watch") "72 Heures" else "Longue durée",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        SpecCard(
                            label = if (product.category == "Watch") "ÉTANCHÉITÉ" else "VOLUME",
                            value = if (product.category == "Watch") "100 Mètres" else "100 ml",
                            modifier = Modifier.weight(1f)
                        )
                        SpecCard(
                            label = if (product.category == "Watch") "BOÎTIER" else "ORIGINE",
                            value = if (product.category == "Watch") "42 mm" else "Paris",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(Modifier.height(32.dp))

                    // Description Title
                    Text(
                        "L'Artisanat",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        product.description + " Conçue pour les collectionneurs exigeants, cette pièce reflète l'excellence sous toutes ses facettes.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White.copy(alpha = 0.7f),
                            lineHeight = 24.sp
                        )
                    )

                    Spacer(Modifier.height(32.dp))

                    // Stock Info
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(AtelierPrimary)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Stock Limité: 3 pièces restantes",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        )
                    }

                    Spacer(Modifier.height(32.dp))

                    // Add to Cart Button
                    Button(
                        onClick = { cartViewModel.addToCart(product) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AtelierPrimary,
                            contentColor = Color.Black
                        )
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Ajouter au panier", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }

                    Spacer(Modifier.height(16.dp))

                    // Secondary Action
                    OutlinedButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        )
                    ) {
                        Text("Personnaliser la gravure", fontWeight = FontWeight.Medium, fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.navigationBarsPadding())
                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun SpecCard(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .border(0.5.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(20.dp)),
        color = AtelierSurface.copy(alpha = 0.4f)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White.copy(alpha = 0.5f),
                    letterSpacing = 1.sp
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}
