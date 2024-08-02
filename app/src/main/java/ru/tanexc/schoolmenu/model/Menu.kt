package ru.tanexc.schoolmenu.model

/**
 * Модель меню на день
 */
data class Menu(
    val dayOfWeek: DayOfWeek = DayOfWeek.Any,
    val meals: List<Meal>
)