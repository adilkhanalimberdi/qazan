package com.css216.qazan.data

import com.css216.qazan.data.repository.FakeRecipeRepository
import com.css216.qazan.data.repository.FavoritesRepository
import com.css216.qazan.data.repository.FridgeRepository
import com.css216.qazan.data.repository.InMemoryFavoritesRepository
import com.css216.qazan.data.repository.InMemoryFridgeRepository
import com.css216.qazan.data.repository.InMemoryShoppingRepository
import com.css216.qazan.data.repository.RecipeRepository
import com.css216.qazan.data.repository.ShoppingRepository

interface AppContainer {
    val recipeRepository: RecipeRepository
    val fridgeRepository: FridgeRepository
    val favoritesRepository: FavoritesRepository
    val shoppingRepository: ShoppingRepository
}

class DefaultAppContainer : AppContainer {
    override val recipeRepository: RecipeRepository = FakeRecipeRepository()
    override val fridgeRepository: FridgeRepository = InMemoryFridgeRepository()
    override val favoritesRepository: FavoritesRepository = InMemoryFavoritesRepository()
    override val shoppingRepository: ShoppingRepository = InMemoryShoppingRepository()
}
