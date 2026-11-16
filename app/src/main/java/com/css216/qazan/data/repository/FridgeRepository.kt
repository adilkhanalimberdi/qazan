package com.css216.qazan.data.repository

import kotlinx.coroutines.flow.Flow

interface FridgeRepository {
    val ingredients: Flow<List<String>>
    suspend fun add(name: String)
    suspend fun remove(name: String)
}
