package ru.tanexc.schoolmenu.data.local.entity.extended

import androidx.room.Embedded
import androidx.room.Relation
import ru.tanexc.schoolmenu.data.local.entity.main.MealEntity
import ru.tanexc.schoolmenu.data.local.entity.supportive.MealDish
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.domain.model.Meal

data class MealWithDishes(
    @Embedded
    val meal: MealEntity,

    @Relation(
        entity = MealDish::class,
        parentColumn = "id",
        entityColumn = "meal"
    )
    val dishes: List<Dish>
)