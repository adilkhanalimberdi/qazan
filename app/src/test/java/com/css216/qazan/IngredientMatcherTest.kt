package com.css216.qazan

import com.css216.qazan.data.model.IngredientMatcher
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class IngredientMatcherTest {
    @Test
    fun matchesNamesIgnoringCaseWhitespaceAndSimplePlurals() {
        assertTrue(IngredientMatcher.matches("  CHICKEN  ", "chicken"))
        assertTrue(IngredientMatcher.matches("Tomatoes", "tomato"))
        assertTrue(IngredientMatcher.matches("eggs", "egg"))
        assertFalse(IngredientMatcher.matches("rice", "chicken"))
    }
}
