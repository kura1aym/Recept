package com.example.recipeapp.model

data class Recipe(
    val id: Long,
    val authorId: Long,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val imageUrl: List<String?>,
    val likes: Long,
    val comments: List<String>,
    val isFavorite: Boolean
)

val recipes = listOf(
    Recipe(
        id = 1,
        authorId = 1,
        title = "Spaghetti Bolognese",
        description = "Classic Italian pasta dish with meat sauce",
        ingredients = listOf("Spaghetti", "Ground beef", "Tomato sauce", "Onion", "Garlic"),
        steps = listOf("Cook spaghetti", "Brown ground beef", "Saute onion and garlic", "Mix everything together"),
        imageUrl = listOf("https://example.com/spaghetti.jpg"),
        likes = 100,
        comments = listOf("Delicious!", "Easy to make"),
        isFavorite = true
    ),
    Recipe(
        id = 2,
        authorId = 2,
        title = "Chicken Alfredo",
        description = "Creamy pasta with chicken and Alfredo sauce",
        ingredients = listOf("Fettuccine", "Chicken breast", "Heavy cream", "Parmesan cheese", "Garlic"),
        steps = listOf("Cook fettuccine", "Cook chicken", "Prepare Alfredo sauce", "Combine everything"),
        imageUrl = listOf("https://example.com/alfredo.jpg"),
        likes = 90,
        comments = listOf("Rich and flavorful", "Perfect comfort food"),
        isFavorite = false
    ),
    Recipe(
        id = 3,
        authorId = 3,
        title = "Vegetarian Stir-Fry",
        description = "Colorful stir-fried vegetables for a quick and healthy meal",
        ingredients = listOf("Broccoli", "Bell peppers", "Carrot", "Snap peas", "Soy sauce"),
        steps = listOf("Stir-fry vegetables", "Season with soy sauce", "Serve hot"),
        imageUrl = listOf("https://example.com/stirfry-vegetarian.jpg"),
        likes = 80,
        comments = listOf("Great vegetarian option", "Light and tasty"),
        isFavorite = true
    ),
    Recipe(
        id = 4,
        authorId = 4,
        title = "Grilled Salmon",
        description = "Healthy grilled salmon with lemon and herbs",
        ingredients = listOf("Salmon fillets", "Lemon", "Olive oil", "Fresh herbs", "Salt and pepper"),
        steps = listOf("Marinate salmon", "Grill until cooked", "Squeeze lemon over the top"),
        imageUrl = listOf("https://example.com/salmon.jpg"),
        likes = 120,
        comments = listOf("Perfectly grilled", "Refreshing with lemon"),
        isFavorite = false
    ),
    Recipe(
        id = 5,
        authorId = 5,
        title = "Mushroom Risotto",
        description = "Creamy and flavorful Italian rice dish with mushrooms",
        ingredients = listOf("Arborio rice", "Mushrooms", "Chicken broth", "Parmesan cheese", "White wine"),
        steps = listOf("Sauté mushrooms", "Cook rice with broth and wine", "Stir in Parmesan"),
        imageUrl = listOf("https://example.com/risotto.jpg"),
        likes = 85,
        comments = listOf("Indulgent and satisfying", "Perfect for a cozy night"),
        isFavorite = true
    ),
    Recipe(
        id = 6,
        authorId = 6,
        title = "Caprese Salad",
        description = "Refreshing salad with tomatoes, mozzarella, and basil",
        ingredients = listOf("Tomatoes", "Fresh mozzarella", "Fresh basil", "Balsamic glaze", "Olive oil"),
        steps = listOf("Slice tomatoes and mozzarella", "Arrange with basil leaves", "Drizzle with balsamic glaze and olive oil"),
        imageUrl = listOf("https://example.com/caprese.jpg"),
        likes = 95,
        comments = listOf("Simple and delicious", "Great for summer"),
        isFavorite = false
    ),
    Recipe(
        id = 7,
        authorId = 7,
        title = "Shrimp Scampi",
        description = "Garlicky shrimp pasta with a hint of lemon",
        ingredients = listOf("Linguine", "Shrimp", "Garlic", "Lemon", "White wine"),
        steps = listOf("Cook linguine", "Sauté shrimp with garlic", "Add lemon and white wine", "Toss with pasta"),
        imageUrl = listOf("https://example.com/scampi.jpg"),
        likes = 110,
        comments = listOf("Garlic lovers' delight", "Light and flavorful"),
        isFavorite = true
    ),
    Recipe(
        id = 8,
        authorId = 8,
        title = "Beef Tacos",
        description = "Classic Mexican street food with seasoned beef and toppings",
        ingredients = listOf("Ground beef", "Taco seasoning", "Tortillas", "Lettuce", "Tomatoes"),
        steps = listOf("Brown beef with taco seasoning", "Assemble tacos with toppings"),
        imageUrl = listOf("https://example.com/tacos.jpg"),
        likes = 88,
        comments = listOf("Family favorite", "Quick and easy dinner"),
        isFavorite = false
    ),
    Recipe(
        id = 9,
        authorId = 9,
        title = "Homemade Pizza",
        description = "Create your own pizza with favorite toppings",
        ingredients = listOf("Pizza dough", "Tomato sauce", "Cheese", "Pepperoni", "Bell peppers"),
        steps = listOf("Roll out pizza dough", "Spread sauce and add toppings", "Bake until crust is golden"),
        imageUrl = listOf("https://example.com/pizza.jpg"),
        likes = 105,
        comments = listOf("Better than delivery", "Endless topping possibilities"),
        isFavorite = true
    ),
    Recipe(
        id = 10,
        authorId = 10,
        title = "Chocolate Chip Cookies",
        description = "Classic homemade cookies with chocolate chips",
        ingredients = listOf("Butter", "Sugar", "Brown sugar", "Flour", "Chocolate chips"),
        steps = listOf("Cream butter and sugars", "Mix in flour and chocolate chips", "Bake until golden"),
        imageUrl = listOf("https://example.com/cookies.jpg"),
        likes = 130,
        comments = listOf("Perfectly chewy", "Irresistible with a glass of milk"),
        isFavorite = false
    )
)
