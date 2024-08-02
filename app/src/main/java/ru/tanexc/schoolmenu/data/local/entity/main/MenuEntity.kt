package ru.tanexc.schoolmenu.data.local.entity.main

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.tanexc.schoolmenu.domain.model.DayOfWeek
import ru.tanexc.schoolmenu.domain.model.Meal

@Entity
class MenuEntity(
    @PrimaryKey
    val menuId: Int,
    val dayOfWeek: DayOfWeek = DayOfWeek.Any,
    val meals: List<Meal>
)