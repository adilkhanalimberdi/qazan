package com.css216.qazan.data.model

data class ShoppingItem(
    val id: Long,
    val name: String,
    val measure: String,
    val source: String?,
    val isBought: Boolean = false
)
