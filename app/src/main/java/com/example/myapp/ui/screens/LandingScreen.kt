package com.example.myapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.R
import com.example.myapp.ui.components.AtelierButton
import com.example.myapp.ui.theme.AtelierBackground
import com.example.myapp.ui.theme.AtelierPrimary

@Composable
fun LandingScreen(
    onNavigateToLogin: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AtelierBackground)
            .clickable { onNavigateToLogin() }
    ) {
        // Main Background Image
        Image(
            painter = painterResource(id = R.drawable.montre2),
            contentDescription = "Premium Watch",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Dark Gradient Overlay for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.5f),
                            AtelierBackground.copy(alpha = 0.9f),
                            AtelierBackground
                        ),
                        startY = 0.5f
                    )
                )
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            // Title Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 60.dp)
            ) {
                Text(
                    text = "ATELIER",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = AtelierPrimary,
                        letterSpacing = 8.sp,
                        fontWeight = FontWeight.Light
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "PRECISION &\nLUXURY",
                    style = MaterialTheme.typography.displayMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 44.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(2.dp)
                        .background(AtelierPrimary)
                )
            }

            Text(
                text = "Experience the art of fine watchmaking and exclusive fragrances crafted for excellence.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                ),
                modifier = Modifier.padding(bottom = 80.dp)
            )

            Text(
                text = "TAP TO ENTER",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = AtelierPrimary,
                    letterSpacing = 4.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
    }
}
