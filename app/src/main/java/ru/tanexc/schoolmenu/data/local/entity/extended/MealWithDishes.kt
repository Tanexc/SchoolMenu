package ru.tanexc.schoolmenu.data.local.entity.extended

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.tanexc.schoolmenu.data.local.entity.DataEntity
import ru.tanexc.schoolmenu.data.local.entity.main.DishEntity
import ru.tanexc.schoolmenu.data.local.entity.main.MealEntity
import ru.tanexc.schoolmenu.data.local.entity.supportive.MealDish
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.domain.model.Domain
import ru.tanexc.schoolmenu.domain.model.Meal

data class MealWithDishes(
    @Embedded
    val meal: MealEntity,

    @Relation(
        entity = DishEntity::class,
        parentColumn = "mealId",
        entityColumn = "dishId",
        associateBy = Junction(MealDish::class)
    )
    val dishes: List<DishEntity>
): DataEntity {
    override fun asDomain(): Meal = Meal(
        meal.mealId,
        dishes.map {it.asDomain()},
        meal.type,
        meal.cost
    )
}