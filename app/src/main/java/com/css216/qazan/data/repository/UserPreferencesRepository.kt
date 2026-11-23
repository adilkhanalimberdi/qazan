package com.css216.qazan.data.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val darkTheme: Flow<Boolean?>
    suspend fun setDarkTheme(enabled: Boolean)
}
