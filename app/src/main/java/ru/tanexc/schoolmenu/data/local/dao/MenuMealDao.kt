package ru.tanexc.schoolmenu.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.tanexc.schoolmenu.data.local.entity.supportive.MenuMeal

@Dao
interface MenuMealDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: List<MenuMeal>)

    @Query("delete from menumeal where mealId = :mealId")
    suspend fun deleteByMeal(mealId: Int)

    @Query("delete from menumeal where menuId = :menuId")
    suspend fun deleteByMenu(menuId: Int)

    @Query("delete from menumeal")
    suspend fun deleteAll()
}