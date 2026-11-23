package com.css216.qazan

import androidx.lifecycle.SavedStateHandle
import com.css216.qazan.data.repository.FakeRecipeRepository
import com.css216.qazan.data.repository.InMemoryFavoritesRepository
import com.css216.qazan.data.repository.InMemoryFridgeRepository
import com.css216.qazan.data.repository.UserPreferencesRepository
import com.css216.qazan.ui.screens.home.HomeContent
import com.css216.qazan.ui.screens.home.HomeViewModel
import com.css216.qazan.ui.screens.search.SearchContent
import com.css216.qazan.ui.screens.search.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest {
    private val preferences = object : UserPreferencesRepository {
        private val selected = MutableStateFlow<Boolean?>(null)
        override val darkTheme = selected
        override suspend fun setDarkTheme(enabled: Boolean) { selected.value = enabled }
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun homeLoadsRecipeAndFridgeCount() = runTest {
        val fridge = InMemoryFridgeRepository()
        val viewModel = HomeViewModel(
            FakeRecipeRepository(), fridge, InMemoryFavoritesRepository(), preferences
        )
        advanceUntilIdle()
        assertTrue(viewModel.uiState.value.content is HomeContent.Success)

        fridge.add("Chicken")
        advanceUntilIdle()
        assertEquals(1, viewModel.uiState.value.fridgeCount)
    }

    @Test
    fun searchMatchesFridgeIngredients() = runTest {
        val fridge = InMemoryFridgeRepository()
        val viewModel = SearchViewModel(
            SavedStateHandle(), FakeRecipeRepository(), fridge, InMemoryFavoritesRepository()
        )
        fridge.add("Chicken")
        advanceUntilIdle()
        val result = viewModel.uiState.value.content as SearchContent.Success
        assertTrue(result.results.isNotEmpty())
        assertTrue(result.results.all { it.matchedCount > 0 })
    }
}
