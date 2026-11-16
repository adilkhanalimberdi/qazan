package com.css216.qazan.ui.screens.shopping

import com.css216.qazan.data.model.ShoppingItem

data class ShoppingUiState(val items: List<ShoppingItem> = emptyList()) {
    val boughtCount: Int get() = items.count { it.isBought }
}
