package com.example.myapp.ui.components

// ✅ ALL imports must be here
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp

import com.example.myapp.ui.theme.AtelierPrimary
import com.example.myapp.ui.theme.AtelierSecondary
import com.example.myapp.ui.theme.AtelierSurface
import com.example.myapp.ui.theme.AtelierTextSecondary
import com.example.myapp.ui.theme.GlassBorder
import com.example.myapp.ui.theme.GlassWhite

@Composable
fun AtelierButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = true,
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPrimary) AtelierPrimary else AtelierSecondary,
            contentColor = if (isPrimary) Color.Black else Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            if (icon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AtelierTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeholder: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelMedium.copy(
                color = AtelierPrimary,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = AtelierTextSecondary.copy(alpha = 0.5f)
                    )
                )
            },
            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = AtelierPrimary
                    )
                }
            },
            trailingIcon = trailingIcon,
            shape = RoundedCornerShape(16.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = AtelierPrimary,
                unfocusedIndicatorColor = Color.White.copy(alpha = 0.1f),
                cursorColor = AtelierPrimary,
                focusedLabelColor = AtelierPrimary
            )
        )
    }
}

@Composable
fun AtelierCard(
    modifier: Modifier = Modifier,
    containerColor: Color = AtelierSurface,
    showBorder: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .then(
                if (showBorder) Modifier.border(
                    width = 1.dp,
                    color = GlassBorder,
                    shape = RoundedCornerShape(24.dp)
                ) else Modifier
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        content = content
    )
}