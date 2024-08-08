package ru.tanexc.schoolmenu.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.tanexc.schoolmenu.data.local.entity.main.DishEntity

@Dao
interface DishDao {

    @Query("select * from dishentity limit :limit offset :offset")
    suspend fun getAll(limit: Int, offset: Int): List<DishEntity>

    @Query("delete from dishentity")
    suspend fun deleteAll()

    @Query("delete from dishentity where dishId = :id")
    suspend fun delete(id: Int)

    @Query("select * from dishentity where dishentity.title like '%' || :title || '%'")
    suspend fun getByTitle(title: String): List<DishEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertDish(data: DishEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDish(data: DishEntity)

}