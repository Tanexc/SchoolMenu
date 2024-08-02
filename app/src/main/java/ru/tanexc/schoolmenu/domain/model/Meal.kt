package ru.tanexc.schoolmenu.domain.model

/**
 * Модель приема пищи
 */
data class Meal(
    val id: Long,
    val dishes: List<Dish>,
    val type: MealType,
    val cost: Float
)
