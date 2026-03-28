package com.example.myapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val price: Double,
    val image: String, // Resource name or URL
    val category: String, // "Watch" or "Perfume"
    val isFavorite: Boolean = false
)
