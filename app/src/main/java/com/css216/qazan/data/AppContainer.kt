package com.css216.qazan.data

import com.css216.qazan.data.repository.FavoritesRepository
import com.css216.qazan.data.repository.FridgeRepository
import com.css216.qazan.data.repository.InMemoryFavoritesRepository
import com.css216.qazan.data.repository.InMemoryFridgeRepository
import com.css216.qazan.data.repository.InMemoryShoppingRepository
import com.css216.qazan.data.repository.NetworkRecipeRepository
import com.css216.qazan.data.repository.RecipeRepository
import com.css216.qazan.data.repository.ShoppingRepository
import com.css216.qazan.data.network.MealDbApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

interface AppContainer {
    val recipeRepository: RecipeRepository
    val fridgeRepository: FridgeRepository
    val favoritesRepository: FavoritesRepository
    val shoppingRepository: ShoppingRepository
}

class DefaultAppContainer : AppContainer {
    private val api: MealDbApiService = Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/api/json/v1/1/")
        .addConverterFactory(Json { ignoreUnknownKeys = true }
            .asConverterFactory(MediaType.parse("application/json")!!))
        .build()
        .create(MealDbApiService::class.java)

    override val recipeRepository: RecipeRepository = NetworkRecipeRepository(api)
    override val fridgeRepository: FridgeRepository = InMemoryFridgeRepository()
    override val favoritesRepository: FavoritesRepository = InMemoryFavoritesRepository()
    override val shoppingRepository: ShoppingRepository = InMemoryShoppingRepository()
}
