package ru.tanexc.schoolmenu.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tanexc.schoolmenu.core.util.DataState
import ru.tanexc.schoolmenu.data.local.dao.DishDao
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.domain.repository.DishRepository

class DishRepositoryImpl(
    private val dishDao: DishDao
): DishRepository {
    override fun getAllDishes(page: Int): Flow<DataState<List<Dish>>> = flow {
        emit(DataState.Loading)

    }

    override fun getByTitle(title: String): Flow<DataState<List<Dish>>> {
        TODO("Not yet implemented")
    }

    override fun insertDish(dish: Dish): Flow<DataState<Unit>> {
        TODO("Not yet implemented")
    }

    override fun updateDish(dish: Dish): Flow<DataState<Unit>> {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Flow<DataState<Unit>> {
        TODO("Not yet implemented")
    }
}