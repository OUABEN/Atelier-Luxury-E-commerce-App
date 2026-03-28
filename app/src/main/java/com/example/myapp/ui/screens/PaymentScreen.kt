package com.example.myapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.ui.CartViewModel
import com.example.myapp.ui.components.AtelierButton
import com.example.myapp.ui.components.AtelierTextField
import com.example.myapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    cartViewModel: CartViewModel,
    onNavigateBack: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    val totalPrice = cartViewModel.getTotalPrice()
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var cardHolder by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(AtelierBackground)) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "PAIEMENT",
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
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                    modifier = Modifier.statusBarsPadding()
                )
            },
            containerColor = Color.Transparent
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Premium Card Visualization
                AtelierCreditCard(
                    number = cardNumber,
                    expiry = expiryDate,
                    holder = cardHolder
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Payment Form
                AtelierTextField(
                    value = cardNumber,
                    onValueChange = { if(it.length <= 16) cardNumber = it },
                    label = "Numéro de carte",
                    leadingIcon = Icons.Default.Create,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = "0000 0000 0000 0000"
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f)) {
                        AtelierTextField(
                            value = expiryDate,
                            onValueChange = { if(it.length <= 5) expiryDate = it },
                            label = "Validité",
                            leadingIcon = Icons.Default.DateRange,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            placeholder = "MM/AA"
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        AtelierTextField(
                            value = cvv,
                            onValueChange = { if(it.length <= 3) cvv = it },
                            label = "CVV",
                            leadingIcon = Icons.Default.Lock,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            visualTransformation = PasswordVisualTransformation(),
                            placeholder = "•••"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                AtelierTextField(
                    value = cardHolder,
                    onValueChange = { cardHolder = it },
                    label = "Titulaire de la carte",
                    leadingIcon = Icons.Default.Person,
                    placeholder = "NOM COMPLET"
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Summary and Pay Button
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .border(0.5.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(24.dp)),
                    color = AtelierSurface.copy(alpha = 0.4f)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Montant Total",
                                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White.copy(alpha = 0.6f))
                            )
                            Text(
                                "€${String.format("%,d", totalPrice.toInt())}",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = AtelierPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        AtelierButton(
                            text = "CONFIRMER LE PAIEMENT",
                            onClick = { isSuccess = true }
                        )
                    }
                }
            }
        }

        // Success Overlay
        AnimatedVisibility(
            visible = isSuccess,
            enter = fadeIn() + expandIn(),
            exit = fadeOut() + shrinkOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.9f))
                    .clickable { onPaymentSuccess() },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        modifier = Modifier.size(100.dp),
                        shape = CircleShape,
                        color = AtelierPrimary,
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.padding(24.dp).size(64.dp),
                            tint = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        "PAIEMENT RÉUSSI",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 4.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Votre commande est en route.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha = 0.6f))
                    )
                }
            }
        }
    }
}

@Composable
fun AtelierCreditCard(number: String, expiry: String, holder: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF1B2431),
                        Color(0xFF0B1019)
                    )
                )
            )
            .border(0.5.dp, AtelierPrimary.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
    ) {
        // Decorative Gold Glow
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = AtelierPrimary.copy(alpha = 0.05f),
                radius = 300f,
                center = center.copy(x = size.width * 0.8f, y = size.height * 0.2f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "PREMIUM CARD",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = AtelierPrimary.copy(alpha = 0.7f),
                        letterSpacing = 2.sp
                    )
                )
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = AtelierPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }

            Text(
                text = if (number.isEmpty()) "•••• •••• •••• ••••" else number.chunked(4).joinToString(" "),
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    letterSpacing = 4.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        "TITULAIRE",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color.White.copy(alpha = 0.4f),
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = if (holder.isEmpty()) "VOTRE NOM" else holder.uppercase(),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "EXPIRE",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color.White.copy(alpha = 0.4f),
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = if (expiry.isEmpty()) "MM/AA" else expiry,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}
