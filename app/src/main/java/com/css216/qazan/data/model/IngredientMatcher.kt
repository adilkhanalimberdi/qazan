package com.css216.qazan.data.model

import java.util.Locale

object IngredientMatcher {
    fun normalize(name: String): String {
        val word = name.trim().lowercase(Locale.ROOT).replace(Regex("\\s+"), " ")
        return when {
            word == "tomatoes" -> "tomato"
            word == "potatoes" -> "potato"
            word.endsWith("ies") -> word.dropLast(3) + "y"
            word.endsWith("s") && !word.endsWith("ss") -> word.dropLast(1)
            else -> word
        }
    }

    fun matches(first: String, second: String): Boolean =
        normalize(first) == normalize(second)
}
