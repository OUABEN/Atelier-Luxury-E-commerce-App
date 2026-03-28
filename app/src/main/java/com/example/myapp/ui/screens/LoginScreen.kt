package com.example.myapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.myapp.ui.AuthState
import com.example.myapp.ui.AuthViewModel
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.myapp.ui.components.AtelierButton
import com.example.myapp.ui.components.AtelierTextField
import com.example.myapp.ui.theme.AtelierBackground
import com.example.myapp.ui.theme.AtelierPrimary
import com.example.myapp.ui.theme.AtelierSurface
import com.example.myapp.ui.theme.AtelierTextSecondary

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onLoginSuccess()
            viewModel.resetAuthState()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = com.example.myapp.R.drawable.montre2),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Dark Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            AtelierBackground.copy(alpha = 0.5f),
                            AtelierBackground.copy(alpha = 0.95f),
                            AtelierBackground
                        ),
                        startY = 0f
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(72.dp))

            // Minimalist Header
            Text(
                text = "ATELIER",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = AtelierPrimary,
                    letterSpacing = 8.sp,
                    fontWeight = FontWeight.Light
                )
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "BIENVENUE",
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                )

                Text(
                    text = "Accédez à votre collection privée.",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
                )

                // Login Form
                AtelierTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Adresse Email",
                    placeholder = "votre@email.com"
                )

                Spacer(modifier = Modifier.height(24.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    AtelierTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Mot de passe",
                        placeholder = "••••••••"
                    )
                    TextButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Text(
                            text = "OUBLIÉ?",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = AtelierPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                AtelierButton(
                    text = "SE CONNECTER",
                    onClick = { viewModel.login(email, password) },
                    icon = Icons.Default.ArrowForward
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sans compte ?",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    )
                    TextButton(onClick = {
                        viewModel.resetAuthState()
                        onNavigateToRegister()
                    }) {
                        Text(
                            text = "INSCRIPTION",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = AtelierPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (authState is AuthState.Error) {
                    Text(
                        text = (authState as AuthState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

