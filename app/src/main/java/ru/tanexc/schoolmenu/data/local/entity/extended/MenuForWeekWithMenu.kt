package ru.tanexc.schoolmenu.data.local.entity.extended

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.tanexc.schoolmenu.data.local.entity.DataEntity
import ru.tanexc.schoolmenu.data.local.entity.main.MenuEntity
import ru.tanexc.schoolmenu.data.local.entity.supportive.MenuForWeek
import ru.tanexc.schoolmenu.domain.model.Menu

data class MenuForWeekWithMenu(
    @Embedded
    val menuForWeek: MenuForWeek,

    @Relation(
        entity = MenuEntity::class,
        parentColumn = "menuId",
        entityColumn = "menuId",
        associateBy = Junction(MenuForWeek::class)
    )
    val menu: MenuWithMeal
): DataEntity {
    override fun asDomain(): Menu = menu.asDomain()
}