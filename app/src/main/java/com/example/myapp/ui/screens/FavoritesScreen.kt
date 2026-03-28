package com.example.myapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.R
import com.example.myapp.data.Product
import com.example.myapp.ui.ProductViewModel
import com.example.myapp.ui.theme.*
import com.example.myapp.ui.utils.getProductImageRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: ProductViewModel,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateBack: () -> Unit
) {
    val favorites by viewModel.favorites.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(AtelierBackground)) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "FAVORITES",
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
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { padding ->
            if (favorites.isEmpty()) {
                EmptyFavoritesState(padding)
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(favorites) { product ->
                        FavoriteItemCard(
                            product = product,
                            onToggleFavorite = { viewModel.toggleFavorite(product) },
                            onClick = { onNavigateToDetail(product.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavoriteItemCard(
    product: Product,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
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
                // Image Placeholder
                val imageRes = getProductImageRes(product.image)
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp),
                    contentScale = ContentScale.Fit
                )

                // Favorite Button Overlay matched to HomeScreen
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
                        Icons.Default.Favorite,
                        contentDescription = "Remove from favorites",
                        modifier = Modifier.size(20.dp),
                        tint = AtelierPrimary
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
fun EmptyFavoritesState(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.05f))
                    .border(1.dp, Color.White.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = AtelierPrimary.copy(alpha = 0.8f)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "SAVED PIECES",
                style = MaterialTheme.typography.titleMedium.copy(
                    letterSpacing = 4.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Light
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Items you mark as favorite will appear here for quick access.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }
    }
}
