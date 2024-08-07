package ru.tanexc.schoolmenu.domain.model

import androidx.room.Entity
import ru.tanexc.schoolmenu.data.local.entity.DataEntity
import ru.tanexc.schoolmenu.data.local.entity.main.MealEntity
import ru.tanexc.schoolmenu.data.local.entity.supportive.MealDish

/**
 * Модель приема пищи
 */
data class Meal(
    val id: Int,
    val dishes: List<Dish>,
    val type: MealType,
    val cost: Float
): Domain {

    companion object {
        fun default(
            id: Int = 0,
            dishes: List<Dish> = emptyList(),
            type: MealType = MealType.Other,
            cost: Float = 0f
        ) = Meal(id, dishes, type, cost)
    }

    override fun asEntity(): MealEntity = MealEntity(
        id,
        type,
        cost
    )

    fun getMealDish(): List<MealDish> = dishes.map { MealDish(mealId = id, dishId = it.id) }
}
