package ru.tanexc.schoolmenu.domain.model

import androidx.room.Entity
import ru.tanexc.schoolmenu.data.local.entity.DataEntity
import ru.tanexc.schoolmenu.data.local.entity.main.MealEntity

/**
 * Модель приема пищи
 */
data class Meal(
    val id: Long,
    val dishes: List<Dish>,
    val type: MealType,
    val cost: Float
): Domain {
    override fun asEntity(): MealEntity = MealEntity(
        id,
        type,
        cost
    )
}
