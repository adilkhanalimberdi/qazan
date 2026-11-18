package com.css216.qazan.data.model

data class MealSummary(
    val id: String,
    val name: String,
    val thumbUrl: String?,
    val category: String = "",
    val area: String = ""
)
