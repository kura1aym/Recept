//package com.example.recipeapp.model
//
//data class Recipe(
//    val id: Long,
//    val authorId: Long,
//    val title: String,
//    val description: String,
//    val category: String,
//    val ingredients: List<String>,
//    val method: List<String>,
//    val imageUrl: List<String?>,
//    val likes: Long,
//    val comments: List<String>,
//    val isFavorite: Boolean
//)
//
//val recipes = listOf(
//    Recipe(
//        id = 1,
//        authorId = 101,
//        title = "Spaghetti Bolognese",
//        description = "Classic Italian pasta dish with rich meat sauce.",
//        category = "Pasta",
//        ingredients = listOf("Spaghetti", "Ground beef", "Tomato sauce", "Onion", "Garlic"),
//        method = listOf("Cook spaghetti", "Brown ground beef", "Sauté onion and garlic", "Mix in tomato sauce", "Combine and simmer"),
//        imageUrl = listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/800px-Good_Food_Display_-_NCI_Visuals_Online.jpg"),
//        likes = 56,
//        comments = listOf("Delicious!", "I love it!"),
//        isFavorite = false
//    ),
//    Recipe(
//        id = 2,
//        authorId = 102,
//        title = "Chicken Alfredo",
//        description = "Creamy Alfredo sauce with grilled chicken over fettuccine pasta.",
//        category = "Pasta",
//        ingredients = listOf("Fettuccine pasta", "Chicken breast", "Heavy cream", "Parmesan cheese", "Garlic"),
//        method = listOf("Cook fettuccine pasta", "Grill chicken breast", "Prepare Alfredo sauce", "Combine and serve"),
//        imageUrl = listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/800px-Good_Food_Display_-_NCI_Visuals_Online.jpg"),
//        likes = 72,
//        comments = listOf("Amazing!", "My favorite pasta dish."),
//        isFavorite = false
//    ),
//    Recipe(
//        id = 3,
//        authorId = 103,
//        title = "Margherita Pizza",
//        description = "Classic pizza with tomato, fresh mozzarella, and basil.",
//        category = "Pizza",
//        ingredients = listOf("Pizza dough", "Tomato sauce", "Fresh mozzarella", "Basil leaves", "Olive oil"),
//        method = listOf("Roll out pizza dough", "Spread tomato sauce", "Top with mozzarella and basil", "Bake until crust is golden"),
//        imageUrl = listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/800px-Good_Food_Display_-_NCI_Visuals_Online.jpg"),
//        likes = 34,
//        comments = listOf("Perfect for pizza night!", "So simple and delicious."),
//        isFavorite = false
//    ),
//    Recipe(
//        id = 4,
//        authorId = 104,
//        title = "Caesar Salad",
//        description = "Classic Caesar salad with crisp romaine lettuce and homemade dressing.",
//        category = "Salad",
//        ingredients = listOf("Romaine lettuce", "Croutons", "Parmesan cheese", "Chicken breast", "Caesar dressing"),
//        method = listOf("Prepare Caesar dressing", "Toss lettuce with dressing", "Top with croutons and Parmesan", "Optional: add grilled chicken"),
//        imageUrl = listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/800px-Good_Food_Display_-_NCI_Visuals_Online.jpg"),
//        likes = 45,
//        comments = listOf("A classic favorite!", "Great for a light lunch."),
//        isFavorite = false
//    ),
//    Recipe(
//        id = 5,
//        authorId = 105,
//        title = "Chocolate Chip Cookies",
//        description = "Soft and chewy chocolate chip cookies.",
//        category = "Dessert",
//        ingredients = listOf("All-purpose flour", "Butter", "Brown sugar", "White sugar", "Chocolate chips"),
//        method = listOf("Cream butter and sugars", "Add dry ingredients", "Fold in chocolate chips", "Bake until golden"),
//        imageUrl = listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/800px-Good_Food_Display_-_NCI_Visuals_Online.jpg"),
//        likes = 88,
//        comments = listOf("Best cookies ever!", "Perfect with a glass of milk."),
//        isFavorite = false
//    ),
//    Recipe(
//        id = 6,
//        authorId = 106,
//        title = "Vegetarian Stir Fry",
//        description = "Healthy stir-fry with colorful vegetables and tofu.",
//        category = "Vegetarian",
//        ingredients = listOf("Tofu", "Broccoli", "Carrots", "Bell peppers", "Soy sauce"),
//        method = listOf("Press and cube tofu", "Stir-fry vegetables and tofu", "Add soy sauce", "Serve over rice or noodles"),
//        imageUrl = listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/800px-Good_Food_Display_-_NCI_Visuals_Online.jpg"),
//        likes = 64,
//        comments = listOf("Delicious and nutritious!", "Great meatless option."),
//        isFavorite = false
//    ),
//    Recipe(
//        id = 7,
//        authorId = 107,
//        title = "Berry Smoothie",
//        description = "Refreshing mixed berry smoothie.",
//        category = "Smoothie",
//        ingredients = listOf("Mixed berries", "Greek yogurt", "Honey", "Almond milk", "Ice"),
//        method = listOf("Blend all ingredients until smooth", "Pour into a glass", "Enjoy immediately!"),
//        imageUrl = listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/800px-Good_Food_Display_-_NCI_Visuals_Online.jpg"),
//        likes = 50,
//        comments = listOf("So tasty!", "Perfect for breakfast."),
//        isFavorite = false
//    ),
//    Recipe(
//        id = 8,
//        authorId = 108,
//        title = "Grilled Salmon",
//        description = "Juicy grilled salmon fillets with a lemon herb marinade.",
//        category = "Seafood",
//        ingredients = listOf("Salmon fillets", "Lemon", "Olive oil", "Garlic", "Fresh herbs"),
//        method = listOf("Prepare marinade with lemon and herbs", "Marinate salmon fillets", "Grill until cooked through", "Serve with side dishes"),
//        imageUrl = listOf("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Good_Food_Display_-_NCI_Visuals_Online.jpg/800px-Good_Food_Display_-_NCI_Visuals_Online.jpg"),
//        likes = 38,
//        comments = listOf("Amazing flavor!", "Perfect for a summer BBQ."),
//        isFavorite = false
//    ),
//)
//
//fun List<Recipe>.getUniqueCategories(): List<String> {
//    return distinctBy { it.category }.map { it.category }
//}
//
//
//val categoryList: List<String> by lazy {
//    recipes.getUniqueCategories()
//}
//
