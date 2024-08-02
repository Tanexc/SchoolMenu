package ru.tanexc.schoolmenu.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.tanexc.schoolmenu.data.local.entity.extended.MealWithDishes
import ru.tanexc.schoolmenu.data.local.entity.main.MealEntity
import ru.tanexc.schoolmenu.domain.model.DayOfWeek
import ru.tanexc.schoolmenu.domain.model.Meal
import ru.tanexc.schoolmenu.domain.model.MealType

@Dao
interface MealDao {

    @Query("select * from mealentity limit :limit offset :offset")
    suspend fun getAllMeal(limit: Int, offset: Int)

    @Query("select * from mealentity where mealentity.type = :type")
    suspend fun getMealByType(type: MealType): List<MealWithDishes>

    @Query("delete from mealentity")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMeal(meal: MealEntity)

    @Update
    suspend fun updateMeal(meal: MealEntity)


}