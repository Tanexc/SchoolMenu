package ru.tanexc.schoolmenu.data.local.entity.supportive

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
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
            parentColumns = ["dishId"],
            childColumns = ["dishId"]
        )
    ]
)
data class MealDish(
    @PrimaryKey
    val id: Int = 0,
    val mealId: Int,
    val dishId: Int
)
