package ru.tanexc.schoolmenu.model

/**
 * Модель приема пищи
 */
data class Meal(
    val id: Long,
    val dishes: List<Dish>,
    val cost: Float
)
