package ru.tanexc.schoolmenu.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tanexc.schoolmenu.core.util.DataState
import ru.tanexc.schoolmenu.data.local.dao.DishDao
import ru.tanexc.schoolmenu.data.local.dao.MealDishDao
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.domain.repository.DishRepository

class DishRepositoryImpl(
    private val dishDao: DishDao,
    private val mealDishDao: MealDishDao
): DishRepository {
    override fun getAllDishes(page: Int): Flow<DataState<List<Dish>>> = flow {
        emit(DataState.Loading)
        try {
            val data = dishDao.getAll(limit = 20, offset = page * 20).map { it.asDomain() }
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: DishRepository::getAllDishes"))
        }
    }

    override fun getByTitle(title: String): Flow<DataState<List<Dish>>> = flow {
        emit(DataState.Loading)
        try {
            val data = dishDao.getByTitle(title).map { it.asDomain() }
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: DishRepository::getByTitle"))
        }
    }

    override fun insertDish(dish: Dish): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        try {
            dishDao.insertDish(dish.asEntity())
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: DishRepository::insertDish"))
        }
    }


    override fun updateDish(dish: Dish): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        try {
            dishDao.updateDish(dish.asEntity())
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: DishRepository::updateDish"))
        }
    }

    override fun deleteAll(): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        try {
            dishDao.deleteAll()
            mealDishDao.deleteAll()
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: DishRepository::deleteAll"))
        }
    }

    override fun delete(id: Int): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        try {
            dishDao.delete(id)
            mealDishDao.deleteByDish(id)
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: DishRepository::delete"))
        }
    }


}