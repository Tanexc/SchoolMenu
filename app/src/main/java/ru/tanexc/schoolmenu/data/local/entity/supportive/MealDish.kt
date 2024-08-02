package ru.tanexc.schoolmenu.data.local.entity.supportive

import androidx.room.Entity
import androidx.room.ForeignKey
import ru.tanexc.schoolmenu.data.local.entity.main.DishEntity
import ru.tanexc.schoolmenu.data.local.entity.main.MealEntity

@Entity(
    foreignKeys = [
        ForeignKey(
            MealEntity::class,
            parentColumns = ["mealId"],
            childColumns = ["mealId"]
        ),
        ForeignKey(
            DishEntity::class,
            parentColumns = ["mealId"],
            childColumns = ["dishId"]
        )
    ]
)
data class MealDish(
    val id: Int,
    val mealId: Int,
    val dishId: Int
)
