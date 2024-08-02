package ru.tanexc.schoolmenu.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.tanexc.schoolmenu.data.local.entity.supportive.MealDish

@Dao
interface MealDishDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: MealDish)

    @Query("delete from mealdish where mealId = :mealId")
    suspend fun deleteByMeal(mealId: Int)

    @Query("delete from mealdish where dishId = :dishId")
    suspend fun deleteByDish(dishId: Int)

    @Query("delete from mealdish")
    suspend fun deleteAll()

}