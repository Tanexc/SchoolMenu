package ru.tanexc.schoolmenu.data.local.entity.main

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.tanexc.schoolmenu.domain.model.MealType

@Entity
data class MealEntity(
    @PrimaryKey
    val mealId: Long,
    val type: MealType,
    val cost: Float
)