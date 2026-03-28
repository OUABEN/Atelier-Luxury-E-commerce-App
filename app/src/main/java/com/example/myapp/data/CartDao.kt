package com.example.myapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartItem: CartItem)

    @Query("DELETE FROM cart_items WHERE id = :id")
    suspend fun removeFromCart(id: Int)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    suspend fun getCartItemByProductId(productId: Int): CartItem?

    @Query("UPDATE cart_items SET quantity = quantity + 1 WHERE productId = :productId")
    suspend fun incrementQuantity(productId: Int)

    @Query("UPDATE cart_items SET quantity = quantity - 1 WHERE productId = :productId AND quantity > 1")
    suspend fun decrementQuantity(productId: Int)
}
