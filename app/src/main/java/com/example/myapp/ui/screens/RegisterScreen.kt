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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.myapp.ui.components.AtelierButton
import com.example.myapp.ui.components.AtelierTextField
import com.example.myapp.ui.theme.AtelierBackground
import com.example.myapp.ui.theme.AtelierPrimary
import com.example.myapp.ui.theme.AtelierTextSecondary

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onRegisterSuccess()
            viewModel.resetAuthState()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = com.example.myapp.R.drawable.montre3),
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
                            AtelierBackground.copy(alpha = 0.4f),
                            AtelierBackground.copy(alpha = 0.9f),
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
            Spacer(modifier = Modifier.height(60.dp))

            // Minimalist Header
            Text(
                text = "ATELIER",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = AtelierPrimary,
                    letterSpacing = 8.sp,
                    fontWeight = FontWeight.Light
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "CRÉER UN COMPTE",
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )

                Text(
                    text = "Rejoignez notre collection exclusive.",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color.White.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.padding(top = 8.dp, bottom = 40.dp)
                )

                // Registration Form
                AtelierTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = "Nom Complet",
                    placeholder = "Jean Dupont",
                    leadingIcon = Icons.Default.Person
                )

                Spacer(modifier = Modifier.height(16.dp))

                AtelierTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Adresse Email",
                    placeholder = "jean.dupont@exemple.com",
                    leadingIcon = Icons.Default.Email
                )

                Spacer(modifier = Modifier.height(16.dp))

                AtelierTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Mot de passe",
                    placeholder = "••••••••",
                    leadingIcon = Icons.Default.Lock
                )

                Spacer(modifier = Modifier.height(40.dp))

                AtelierButton(
                    text = "C’EST PARTI",
                    onClick = { viewModel.register(fullName, email, password) }
                )

                Spacer(modifier = Modifier.height(11.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Déjà un profil ?",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    )
                    TextButton(onClick = {
                        viewModel.resetAuthState()
                        onNavigateToLogin()
                    }) {
                        Text(
                            text = "SE CONNECTER",
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

