package ru.tanexc.schoolmenu.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.tanexc.schoolmenu.data.local.entity.extended.MenuWithMeal
import ru.tanexc.schoolmenu.data.local.entity.main.MenuEntity
import ru.tanexc.schoolmenu.domain.model.DayOfWeek

@Dao
interface MenuDao {
    @Query("select * from menuentity limit :limit offset :offset")
    suspend fun getAllMenu(limit: Int, offset: Int)

    @Query("select * from menuentity where menuentity.dayOfWeek = :day")
    suspend fun getMenuByDay(day: DayOfWeek): List<MenuWithMeal>

    @Query("delete from menuentity")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMenu(menu: MenuEntity)

    @Update
    suspend fun updateMenu(menu: MenuEntity)
}