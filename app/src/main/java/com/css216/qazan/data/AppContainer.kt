package com.css216.qazan.data

import android.content.Context
import androidx.room.Room
import com.css216.qazan.data.local.QazanDatabase
import com.css216.qazan.data.repository.FavoritesRepository
import com.css216.qazan.data.repository.FridgeRepository
import com.css216.qazan.data.repository.NetworkRecipeRepository
import com.css216.qazan.data.repository.OfflineFavoritesRepository
import com.css216.qazan.data.repository.OfflineFridgeRepository
import com.css216.qazan.data.repository.OfflineShoppingRepository
import com.css216.qazan.data.repository.RecipeRepository
import com.css216.qazan.data.repository.ShoppingRepository
import com.css216.qazan.data.network.MealDbApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

interface AppContainer {
    val recipeRepository: RecipeRepository
    val fridgeRepository: FridgeRepository
    val favoritesRepository: FavoritesRepository
    val shoppingRepository: ShoppingRepository
}

class DefaultAppContainer(context: Context) : AppContainer {
    private val database = Room.databaseBuilder(
        context.applicationContext,
        QazanDatabase::class.java,
        "qazan.db"
    ).build()
    private val api: MealDbApiService = Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/api/json/v1/1/")
        .addConverterFactory(Json { ignoreUnknownKeys = true }
            .asConverterFactory("application/json".toMediaType()))
        .build()
        .create(MealDbApiService::class.java)

    override val recipeRepository: RecipeRepository = NetworkRecipeRepository(api)
    override val fridgeRepository: FridgeRepository = OfflineFridgeRepository(database.fridgeDao())
    override val favoritesRepository: FavoritesRepository = OfflineFavoritesRepository(database.favoriteDao())
    override val shoppingRepository: ShoppingRepository = OfflineShoppingRepository(database.shoppingDao())
}
