package ru.tanexc.schoolmenu.data.local.entity.supportive

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MenuForWeek(
    @PrimaryKey
    val id: Int,
    val menuId: Int,

)