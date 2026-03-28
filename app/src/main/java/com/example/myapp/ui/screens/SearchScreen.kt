package com.example.myapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.R
import com.example.myapp.data.Product
import com.example.myapp.ui.ProductViewModel
import com.example.myapp.ui.theme.*
import com.example.myapp.ui.utils.getProductImageRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: ProductViewModel,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val products by viewModel.products.collectAsState()
    val filteredProducts = products.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true)
    }

    val categories = listOf("All", "Watch", "Perfume", "Accessories")
    var selectedCategory by remember { mutableStateOf("All") }
    val finalProducts = if (selectedCategory == "All") filteredProducts else filteredProducts.filter { it.category.equals(selectedCategory, ignoreCase = true) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .background(AtelierBackground)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "DISCOVER",
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

                Spacer(modifier = Modifier.height(8.dp))

                // Premium Search Bar
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .border(0.5.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(24.dp)),
                    placeholder = {
                        Text("Rechercher...", color = AtelierTextSecondary, style = MaterialTheme.typography.bodyLarge)
                    },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = AtelierPrimary) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = AtelierTextSecondary)
                            }
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = AtelierSurface.copy(alpha = 0.6f),
                        unfocusedContainerColor = AtelierSurface.copy(alpha = 0.4f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = AtelierPrimary
                    ),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Filters
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(categories) { category ->
                        val isSelected = selectedCategory == category
                        Surface(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { selectedCategory = category }
                                .border(
                                    0.5.dp,
                                    if (isSelected) AtelierPrimary else Color.White.copy(alpha = 0.1f),
                                    CircleShape
                                ),
                            color = if (isSelected) AtelierPrimary.copy(alpha = 0.1f) else Color.Transparent
                        ) {
                            Text(
                                text = if(category == "All") "Tout" else category,
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) AtelierPrimary else Color.White.copy(alpha = 0.6f)
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        },
        containerColor = AtelierBackground
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    "RÉSULTATS (${finalProducts.size})",
                    style = MaterialTheme.typography.labelMedium.copy(
                        letterSpacing = 2.sp,
                        color = AtelierTextSecondary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 8.dp, start = 8.dp)
                )
            }

            items(finalProducts) { product ->
                PremiumSearchResultItem(product = product) {
                    onNavigateToDetail(product.id)
                }
            }

            if (finalProducts.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = Color.White.copy(alpha = 0.1f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "Aucun résultat trouvé",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = AtelierTextSecondary,
                                    letterSpacing = 1.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PremiumSearchResultItem(product: Product, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = AtelierSurface.copy(alpha = 0.4f),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.05f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image Placeholder/Thumbnail
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
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
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    product.category.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = AtelierPrimary,
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    product.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "€${String.format("%,d", product.price.toInt())}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Light
                    )
                )
            }

            Surface(
                modifier = Modifier.size(36.dp),
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.05f)
            ) {
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = AtelierPrimary,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
