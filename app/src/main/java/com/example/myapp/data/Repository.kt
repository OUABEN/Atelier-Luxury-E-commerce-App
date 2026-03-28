package com.example.myapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(
    private val userDao: UserDao,
    private val productDao: ProductDao,
    private val cartDao: CartDao
) {


    // User operations
    suspend fun registerUser(user: User) = userDao.registerUser(user)
    suspend fun loginUser(email: String, password: String) = userDao.loginUser(email, password)
    suspend fun getUserByEmail(email: String) = userDao.getUserByEmail(email)

    // Product operations
    suspend fun getAllProducts() = productDao.getAllProducts()
    suspend fun getProductsByCategory(category: String) = productDao.getProductsByCategory(category)
    suspend fun getProductById(id: Int) = productDao.getProductById(id)
    suspend fun getFavorites() = productDao.getFavorites()
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) = productDao.updateFavoriteStatus(id, isFavorite)
    // Cart operations
    fun getCartItems(): Flow<List<CartItem>> = cartDao.getCartItems()

    suspend fun addToCart(product: Product) {
        val existingItem = cartDao.getCartItemByProductId(product.id)
        if (existingItem != null) {
            cartDao.incrementQuantity(product.id)
        } else {
            val cartItem = CartItem(
                productId = product.id,
                name = product.name,
                price = product.price,
                image = product.image,
                quantity = 1
            )
            cartDao.addToCart(cartItem)
        }
    }

    suspend fun removeFromCart(id: Int) = cartDao.removeFromCart(id)
    suspend fun clearCart() = cartDao.clearCart()
    suspend fun incrementQuantity(productId: Int) = cartDao.incrementQuantity(productId)
    suspend fun decrementQuantity(productId: Int) = cartDao.decrementQuantity(productId)


    // Mock data population
    suspend fun populateData() {
        val existing = productDao.getAllProducts()
        if (existing.isEmpty() || existing.size < 6) { // Re-populate if missing or old data
            val mockProducts = listOf(
                Product(name = "Elegant Midnight Chronograph", description = "A masterpiece of horological excellence, featuring a hand-polished obsidian ceramic bezel and a sapphire crystal case back.", price = 12450.00, image = "montre2", category = "Watch"),
                Product(name = "Heritage Moonphase", description = "Classic design with moonphase complication and premium leather strap.", price = 15800.00, image = "montre4", category = "Watch"),
                Product(name = "Aero Chronograph", description = "Precision timing for the modern aviator, crafted in brushed titanium.", price = 8900.00, image = "montre5", category = "Watch"),
                Product(name = "Oceanic Diver Premium", description = "Water-resistant up to 300m with a luminous dial and ceramic bezel.", price = 11200.00, image = "montre3", category = "Watch"),
                Product(name = "Noir de Nuit", description = "The perfect companion for the Midnight Chronograph. A scent that lingers like a shadow.", price = 320.00, image = "perfume", category = "Perfume"),
                Product(name = "Nocturnal Oud", description = "Deep and mysterious oud with notes of sandalwood and amber.", price = 205.00, image = "parfume2", category = "Perfume"),
                Product(name = "Silk Santal", description = "Smooth sandalwood accord with a touch of vanilla and spice.", price = 185.00, image = "parfume3", category = "Perfume"),
                Product(name = "Royal Amber", description = "A rich, warm fragrance with notes of honeyed amber and exotic spices.", price = 245.00, image = "parfume4", category = "Perfume")
            )
            productDao.insertProducts(mockProducts)
        }
    }

}
