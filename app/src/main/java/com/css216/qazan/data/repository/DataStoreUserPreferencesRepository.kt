package com.css216.qazan.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import java.io.IOException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.userPreferencesDataStore by preferencesDataStore(name = "user_preferences")

class DataStoreUserPreferencesRepository(context: Context) : UserPreferencesRepository {
    private val store = context.applicationContext.userPreferencesDataStore
    private val darkThemeKey = booleanPreferencesKey("dark_theme")

    override val darkTheme = store.data.catch { error ->
        if (error is IOException) emit(emptyPreferences()) else throw error
    }.map { preferences -> preferences[darkThemeKey] }

    override suspend fun setDarkTheme(enabled: Boolean) {
        store.edit { preferences -> preferences[darkThemeKey] = enabled }
    }
}
