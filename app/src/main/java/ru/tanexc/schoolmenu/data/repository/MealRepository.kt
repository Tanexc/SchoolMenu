package ru.tanexc.schoolmenu.data.repository

import kotlinx.coroutines.flow.Flow
import ru.tanexc.schoolmenu.core.util.DataState
import ru.tanexc.schoolmenu.domain.model.Meal
import ru.tanexc.schoolmenu.domain.model.MealType

interface MealRepository {

    fun getAllMeal(page: Int): Flow<DataState<List<Meal>>>

    fun getMealByType(type: MealType): Flow<DataState<List<Meal>>>

    fun deleteAll(): Flow<DataState<Unit>>

    fun delete(id: Int): Flow<DataState<Unit>>

    fun insert(meal: Meal): Flow<DataState<Unit>>

    fun update(meal: Meal): Flow<DataState<Unit>>
}