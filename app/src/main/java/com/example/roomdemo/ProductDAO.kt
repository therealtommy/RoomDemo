package com.example.roomdemo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(product: Product)
    @Query("SELECT * FROM products WHERE productName = :name")
    suspend fun findProduct(name: String): List<Product>
        @Query("DELETE FROM products WHERE productName = :name")
    suspend fun deleteProduct(name: String)
            @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>
}