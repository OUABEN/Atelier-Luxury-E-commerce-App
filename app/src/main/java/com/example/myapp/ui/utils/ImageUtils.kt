package com.example.myapp.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.myapp.R

@Composable
fun getProductImageRes(imageName: String): Int {
    val context = LocalContext.current
    val resId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
    
    return if (resId != 0) {
        resId
    } else {
        // Fallback to defaults based on name if not found by strict identifier
        when {
            imageName.contains("montre", ignoreCase = true) -> R.drawable.montres
            imageName.contains("perfume", ignoreCase = true) -> R.drawable.perfume
            imageName.contains("parfum", ignoreCase = true) -> R.drawable.perfume
            else -> R.drawable.montres
        }
    }
}
