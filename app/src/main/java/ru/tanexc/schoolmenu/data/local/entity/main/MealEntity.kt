package ru.tanexc.schoolmenu.data.local.entity.main

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.tanexc.schoolmenu.data.local.entity.DataEntity
import ru.tanexc.schoolmenu.domain.model.Domain
import ru.tanexc.schoolmenu.domain.model.Meal
import ru.tanexc.schoolmenu.domain.model.MealType

@Entity
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val mealId: Int,
    val type: MealType,
    val cost: Float
): DataEntity {
    override fun asDomain(): Meal = Meal(
        mealId,
        emptyList(),
        type,
        cost
    )
}