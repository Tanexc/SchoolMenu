package ru.tanexc.schoolmenu.domain.model

import ru.tanexc.schoolmenu.data.local.entity.main.MenuEntity
import ru.tanexc.schoolmenu.data.local.entity.supportive.MenuMeal

/**
 * Модель меню на день
 */
data class Menu(
    val id: Int,
    val dayOfWeek: DayOfWeek = DayOfWeek.Any,
    val meals: List<Meal>
) : Domain {
    override fun asEntity(): MenuEntity = MenuEntity(
        id,
        dayOfWeek
    )

    fun getMenuMeal(): List<MenuMeal> = meals.map { MenuMeal(menuId = id, mealId = it.id) }
}