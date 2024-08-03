package ru.tanexc.schoolmenu.data.repository

import kotlinx.coroutines.flow.Flow
import ru.tanexc.schoolmenu.core.util.DataState
import ru.tanexc.schoolmenu.domain.model.Dish

interface DishRepository {
    fun getAllDishes(page: Int): Flow<DataState<List<Dish>>>

    fun getByTitle(title: String): Flow<DataState<List<Dish>>>

    fun insertDish(dish: Dish): Flow<DataState<Unit>>

    fun updateDish(dish: Dish): Flow<DataState<Unit>>

    fun deleteAll(): Flow<DataState<Unit>>

    fun delete(id: Int): Flow<DataState<Unit>>
}