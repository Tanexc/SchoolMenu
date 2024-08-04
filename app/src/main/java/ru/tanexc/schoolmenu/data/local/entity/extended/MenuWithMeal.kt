package ru.tanexc.schoolmenu.data.local.entity.extended

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.tanexc.schoolmenu.data.local.entity.DataEntity
import ru.tanexc.schoolmenu.data.local.entity.main.MealEntity
import ru.tanexc.schoolmenu.data.local.entity.main.MenuEntity
import ru.tanexc.schoolmenu.data.local.entity.supportive.MenuMeal
import ru.tanexc.schoolmenu.domain.model.Domain
import ru.tanexc.schoolmenu.domain.model.Meal
import ru.tanexc.schoolmenu.domain.model.Menu

data class MenuWithMeal(
    @Embedded
    val menu: MenuEntity,

    @Relation(
        entity = MealEntity::class,
        parentColumn = "menuId",
        entityColumn = "mealId",
        associateBy = Junction(MenuMeal::class)
    )
    val meals: List<MealEntity>
): DataEntity {
    override fun asDomain(): Menu = Menu(
        menu.menuId,
        menu.dayOfWeek,
        meals.map { it.asDomain() }
    )
}