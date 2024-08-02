package ru.tanexc.schoolmenu.data.local.entity.supportive

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.tanexc.schoolmenu.data.local.entity.main.DishEntity
import ru.tanexc.schoolmenu.data.local.entity.main.MealEntity
import ru.tanexc.schoolmenu.data.local.entity.main.MenuEntity

@Entity(
    foreignKeys = [
        ForeignKey(
            MealEntity::class,
            parentColumns = ["mealId"],
            childColumns = ["mealId"]
        ),
        ForeignKey(
            MenuEntity::class,
            parentColumns = ["menuId"],
            childColumns = ["menuId"]
        )
    ]
)
data class MenuMeal(
    @PrimaryKey
    val id: Int,
    val menuId: Int,
    val mealId: Int
)