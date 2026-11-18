package com.css216.qazan.data.fake

import com.css216.qazan.data.model.Ingredient
import com.css216.qazan.data.model.Meal

object FakeRecipeData {
    val meals = listOf(
        Meal(
            id = "1001",
            name = "Chicken & Rice",
            category = "Chicken",
            area = "Indian",
            instructions = "1. Rinse the rice. Chop the onion, tomato and garlic.\n\n2. Heat the oil, soften the onion and garlic, then add diced chicken. Cook thoroughly.\n\n3. Add tomatoes, rice and 360 ml water. Cover and simmer until the rice is tender and the chicken is cooked through.",
            thumbUrl = null,
            ingredients = listOf(
                Ingredient("Chicken", "300 g"),
                Ingredient("Rice", "180 g"),
                Ingredient("Onion", "1"),
                Ingredient("Tomato", "2"),
                Ingredient("Garlic", "2 cloves"),
                Ingredient("Olive oil", "1 tbsp")
            )
        ),
        Meal(
            id = "1002",
            name = "Tomato Pasta",
            category = "Pasta",
            area = "Italian",
            instructions = "1. Cook the pasta in boiling water until tender.\n\n2. Warm the olive oil, add chopped garlic and tomatoes, and simmer for 10 minutes.\n\n3. Toss with the drained pasta and finish with grated cheese.",
            thumbUrl = null,
            ingredients = listOf(
                Ingredient("Pasta", "200 g"),
                Ingredient("Tomatoes", "3"),
                Ingredient("Garlic", "2 cloves"),
                Ingredient("Olive oil", "2 tbsp"),
                Ingredient("Cheese", "40 g")
            )
        ),
        Meal(
            id = "1003",
            name = "Garden Omelette",
            category = "Vegetarian",
            area = "French",
            instructions = "1. Beat the eggs with milk. Dice the tomato and grate the cheese.\n\n2. Melt the butter in a pan, add the tomato and pour in the eggs.\n\n3. Cook gently until set, sprinkle with cheese and fold.",
            thumbUrl = null,
            ingredients = listOf(
                Ingredient("Eggs", "3"),
                Ingredient("Tomato", "1"),
                Ingredient("Milk", "2 tbsp"),
                Ingredient("Cheese", "30 g"),
                Ingredient("Butter", "10 g")
            )
        ),
        Meal(
            id = "1004",
            name = "Potato Soup",
            category = "Vegetarian",
            area = "British",
            instructions = "1. Peel and dice the vegetables. Soften the onion in butter.\n\n2. Add potato, carrot and 600 ml water. Simmer until soft.\n\n3. Mash lightly, add milk and warm through before serving.",
            thumbUrl = null,
            ingredients = listOf(
                Ingredient("Potatoes", "3"),
                Ingredient("Onion", "1"),
                Ingredient("Carrot", "1"),
                Ingredient("Milk", "100 ml"),
                Ingredient("Butter", "20 g")
            )
        ),
        Meal(
            id = "1005",
            name = "Beef & Vegetable Stew",
            category = "Beef",
            area = "British",
            instructions = "1. Cut the beef into cubes and brown in the oil.\n\n2. Add chopped vegetables and enough water to cover.\n\n3. Cover and simmer gently until the beef is tender and the vegetables are cooked.",
            thumbUrl = null,
            ingredients = listOf(
                Ingredient("Beef", "400 g"),
                Ingredient("Potato", "2"),
                Ingredient("Carrot", "2"),
                Ingredient("Onion", "1"),
                Ingredient("Tomatoes", "2"),
                Ingredient("Olive oil", "1 tbsp")
            )
        ),
        Meal(
            id = "1006",
            name = "Lemon Fish",
            category = "Seafood",
            area = "Mediterranean",
            instructions = "1. Place fish fillets in a baking dish.\n\n2. Mix lemon juice, crushed garlic and olive oil. Spoon over the fish.\n\n3. Bake at 190 C until the fish is opaque and flakes easily, about 15 to 20 minutes.",
            thumbUrl = null,
            ingredients = listOf(
                Ingredient("Fish", "2 fillets"),
                Ingredient("Lemon", "1"),
                Ingredient("Garlic", "1 clove"),
                Ingredient("Olive oil", "1 tbsp")
            )
        ),
        Meal(
            id = "1007",
            name = "Mushroom Rice",
            category = "Vegetarian",
            area = "Italian",
            instructions = "1. Slice the mushrooms and onion. Cook in butter until softened.\n\n2. Stir in rice, add hot water a little at a time and stir regularly.\n\n3. When the rice is tender, fold in grated cheese and serve.",
            thumbUrl = null,
            ingredients = listOf(
                Ingredient("Rice", "180 g"),
                Ingredient("Mushrooms", "200 g"),
                Ingredient("Onion", "1"),
                Ingredient("Butter", "20 g"),
                Ingredient("Cheese", "30 g")
            )
        ),
        Meal(
            id = "1008",
            name = "Banana Pancakes",
            category = "Dessert",
            area = "American",
            instructions = "1. Mash the banana. Whisk with the egg and milk, then stir in the flour.\n\n2. Melt a little butter in a pan over medium heat.\n\n3. Cook small spoonfuls of batter on both sides until golden and set.",
            thumbUrl = null,
            ingredients = listOf(
                Ingredient("Banana", "1"),
                Ingredient("Egg", "1"),
                Ingredient("Flour", "100 g"),
                Ingredient("Milk", "150 ml"),
                Ingredient("Butter", "10 g")
            )
        ),
        Meal(
            id = "1009",
            name = "Chickpea Salad",
            category = "Vegetarian",
            area = "Mediterranean",
            instructions = "1. Drain the cooked chickpeas and chop the vegetables.\n\n2. Toss everything in a bowl.\n\n3. Dress with lemon juice and olive oil and serve.",
            thumbUrl = null,
            ingredients = listOf(
                Ingredient("Chickpeas", "200 g cooked"),
                Ingredient("Tomato", "2"),
                Ingredient("Cucumber", "1"),
                Ingredient("Lemon", "1/2"),
                Ingredient("Olive oil", "1 tbsp")
            )
        ),
        Meal(
            id = "1010",
            name = "Chicken Noodles",
            category = "Chicken",
            area = "Chinese",
            instructions = "1. Cook the noodles according to the packet.\n\n2. Stir-fry sliced chicken in oil until cooked through, then add carrot and garlic.\n\n3. Toss in noodles and soy sauce. Heat through and serve.",
            thumbUrl = null,
            ingredients = listOf(
                Ingredient("Chicken", "250 g"),
                Ingredient("Noodles", "180 g"),
                Ingredient("Carrot", "1"),
                Ingredient("Garlic", "2 cloves"),
                Ingredient("Soy sauce", "2 tbsp"),
                Ingredient("Olive oil", "1 tbsp")
            )
        )
    )
}
