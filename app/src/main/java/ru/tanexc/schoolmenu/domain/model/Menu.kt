package ru.tanexc.schoolmenu.domain.model

/**
 * Модель меню на день
 */
data class Menu(
    val id: Int,
    val dayOfWeek: DayOfWeek = DayOfWeek.Any,
    val meals: List<Meal>
)