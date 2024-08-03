package ru.tanexc.schoolmenu.data.local.entity.main

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.tanexc.schoolmenu.data.local.entity.DataEntity
import ru.tanexc.schoolmenu.domain.model.DayOfWeek
import ru.tanexc.schoolmenu.domain.model.Domain
import ru.tanexc.schoolmenu.domain.model.Meal
import ru.tanexc.schoolmenu.domain.model.Menu

@Entity
class MenuEntity(
    @PrimaryKey
    val menuId: Int,
    val dayOfWeek: DayOfWeek = DayOfWeek.Any,
): DataEntity {
    override fun asDomain(): Menu = Menu(
        menuId,
        dayOfWeek,
        emptyList()
    )
}