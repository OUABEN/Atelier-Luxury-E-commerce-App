package com.example.myapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.R
import com.example.myapp.data.Product
import com.example.myapp.ui.ProductViewModel
import com.example.myapp.ui.components.AtelierCard
import com.example.myapp.ui.theme.*
import com.example.myapp.ui.utils.getProductImageRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ProductViewModel,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToFavorites: () -> Unit
) {
    val products by viewModel.products.collectAsState()
    var selectedCategory by remember { mutableStateOf("All") }
    val scrollState = rememberLazyStaggeredGridState()

    Box(modifier = Modifier.fillMaxSize().background(AtelierBackground)) {
        Scaffold(
            topBar = {
                TransparentTopBar(
                    onMenuClick = { /* TODO */ },
                    onCartClick = onNavigateToCart
                )
            },
            bottomBar = {
                PremiumBottomBar(
                    onNavigateToProfile = onNavigateToProfile,
                    onNavigateToSearch = onNavigateToSearch,
                    onNavigateToFavorites = onNavigateToFavorites
                )
            },
            containerColor = Color.Transparent
        ) { padding ->
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalItemSpacing = 16.dp,
                state = scrollState
            ) {
                // Hero Section
                item(span = StaggeredGridItemSpan.FullLine) {
                    ModernHeroSection()
                }

                // Categories
                item(span = StaggeredGridItemSpan.FullLine) {
                    ElegantCategorySection(
                        selectedCategory = selectedCategory,
                        onCategorySelected = { category ->
                            selectedCategory = category
                            if (category == "All") viewModel.loadProducts()
                            else viewModel.filterByCategory(category)
                        }
                    )
                }

                // Product Grid with Animations
                itemsIndexed(products) { index, product ->
                    val state = remember { MutableTransitionState(false).apply { targetState = true } }
                    AnimatedVisibility(
                        visibleState = state,
                        enter = fadeIn(animationSpec = tween(durationMillis = 600, delayMillis = index * 100)) +
                                slideInVertically(animationSpec = tween(durationMillis = 600, delayMillis = index * 100)) { 50 }
                    ) {
                        AtelierProductCard(
                            product = product,
                            onToggleFavorite = { viewModel.toggleFavorite(product) }
                        ) {
                            onNavigateToDetail(product.id)
                        }
                    }
                }

                item(span = StaggeredGridItemSpan.FullLine) {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentTopBar(onMenuClick: () -> Unit, onCartClick: () -> Unit) {
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
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
            }
        },
        actions = {
            IconButton(onClick = onCartClick) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
fun ModernHeroSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clip(RoundedCornerShape(32.dp))
            .border(0.5.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(32.dp))
    ) {
        // Main Background Image (Principal Element)
        Image(
            painter = painterResource(id = R.drawable.montre2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient Overlay for content readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.8f),
                            Color.Black.copy(alpha = 0.4f),
                            Color.Transparent
                        ),
                        startX = 0f,
                        endX = 1000f
                    )
                )
        )

        // Overlay Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                color = AtelierPrimary.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp),
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "NOUVELLE\nCOLLECTION",
                style = MaterialTheme.typography.displaySmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 36.sp,
                    letterSpacing = 2.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "La précision suisse,\nl'élégance parisienne.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White.copy(alpha = 0.7f),
                    lineHeight = 24.sp
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Surface(
                onClick = { /* TODO */ },
                color = AtelierPrimary,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "EXPLORER",
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
            }
        }
    }
}

@Composable
fun ElegantCategorySection(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    val categories = listOf("All", "Watch", "Perfume", "Accessories")

    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            "LES COLLECTIONS",
            style = MaterialTheme.typography.labelLarge.copy(
                color = AtelierPrimary,
                letterSpacing = 2.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            categories.forEach { category ->
                CategoryPill(
                    text = if(category == "All") "Tout" else category,
                    isSelected = selectedCategory == category,
                    onClick = { onCategorySelected(category) }
                )
            }
        }
    }
}

@Composable
fun CategoryPill(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .border(
                0.5.dp,
                if (isSelected) AtelierPrimary else Color.White.copy(alpha = 0.1f),
                CircleShape
            ),
        color = if (isSelected) AtelierPrimary.copy(alpha = 0.1f) else Color.Transparent
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) AtelierPrimary else Color.White.copy(alpha = 0.6f)
            )
        )
    }
}

@Composable
fun AtelierProductCard(product: Product, onToggleFavorite: () -> Unit, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() }
            .border(0.5.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(24.dp)),
        color = AtelierSurface.copy(alpha = 0.4f)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.White.copy(alpha = 0.02f)),
                contentAlignment = Alignment.Center
            ) {
                val imageRes = getProductImageRes(product.image)
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp),
                    contentScale = ContentScale.Fit
                )

                // Glass Favorite Button
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.4f))
                        .clickable { onToggleFavorite() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (product.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = if (product.isFavorite) AtelierPrimary else Color.White
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    product.category.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = AtelierPrimary,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    product.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "€${String.format("%,d", product.price.toInt())}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Light
                        )
                    )

                    Surface(
                        modifier = Modifier.size(28.dp),
                        shape = CircleShape,
                        color = AtelierPrimary
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PremiumBottomBar(
    onNavigateToProfile: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToFavorites: () -> Unit
) {
    Surface(
        color = Color(0xFF0F0F0F).copy(alpha = 0.95f),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .border(0.5.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val items = listOf(
                Triple(Icons.Default.Home, "Home", true),
                Triple(Icons.Default.Search, "Search", false),
                Triple(Icons.Default.FavoriteBorder, "Saved", false),
                Triple(Icons.Default.Person, "Profile", false)
            )

            items.forEach { (icon, label, selected) ->
                IconButton(onClick = {
                    when (label) {
                        "Profile" -> onNavigateToProfile()
                        "Search" -> onNavigateToSearch()
                        "Saved" -> onNavigateToFavorites()
                    }
                }) {
                    Icon(
                        icon,
                        contentDescription = label,
                        modifier = Modifier.size(26.dp),
                        tint = if (selected) AtelierPrimary else Color.White.copy(alpha = 0.4f)
                    )
                }
            }
        }
    }
}

