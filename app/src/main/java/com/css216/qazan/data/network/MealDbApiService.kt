package com.css216.qazan.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface MealDbApiService {
    @GET("random.php")
    suspend fun random(): MealsResponse

    @GET("categories.php")
    suspend fun categories(): CategoriesResponse

    @GET("lookup.php")
    suspend fun lookup(@Query("i") id: String): MealsResponse

    @GET("search.php")
    suspend fun search(@Query("s") query: String): MealsResponse

    @GET("filter.php")
    suspend fun filterByIngredient(@Query("i") ingredient: String): MealSummariesResponse

    @GET("filter.php")
    suspend fun filterByCategory(@Query("c") category: String): MealSummariesResponse

    @GET("list.php?i=list")
    suspend fun ingredientList(): IngredientListResponse
}
