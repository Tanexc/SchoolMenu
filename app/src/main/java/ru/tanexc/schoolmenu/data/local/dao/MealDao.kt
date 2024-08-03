package ru.tanexc.schoolmenu.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.tanexc.schoolmenu.data.local.entity.extended.MealWithDishes
import ru.tanexc.schoolmenu.data.local.entity.main.MealEntity
import ru.tanexc.schoolmenu.domain.model.MealType

@Dao
interface MealDao {

    @Query("select * from mealentity limit :limit offset :offset")
    suspend fun getAllMeal(limit: Int, offset: Int): List<MealWithDishes>

    @Query("select * from mealentity where mealentity.type = :type")
    suspend fun getMealByType(type: MealType): List<MealWithDishes>

    @Query("delete from mealentity")
    suspend fun deleteAll()

    @Query("delete from mealentity where mealId = :id")
    suspend fun delete(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(meal: MealEntity)

    @Update
    suspend fun update(meal: MealEntity)


}