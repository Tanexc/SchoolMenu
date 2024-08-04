package ru.tanexc.schoolmenu.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tanexc.schoolmenu.core.util.DataState
import ru.tanexc.schoolmenu.data.local.dao.MealDao
import ru.tanexc.schoolmenu.data.local.dao.MealDishDao
import ru.tanexc.schoolmenu.data.local.dao.MenuMealDao
import ru.tanexc.schoolmenu.domain.model.Meal
import ru.tanexc.schoolmenu.domain.model.MealType
import ru.tanexc.schoolmenu.domain.repository.MealRepository

class MealRepositoryImpl(
    private val mealDao: MealDao,
    private val mealDishDao: MealDishDao,
    private val menuMealDao: MenuMealDao
) : MealRepository {
    override fun getAllMeal(page: Int): Flow<DataState<List<Meal>>> = flow {
        emit(DataState.Loading)
        try {
            val data = mealDao.getAllMeal(20, page * 20).map { it.asDomain() }
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: MealRepositoryImpl::getAllMeal"))
        }
    }

    override fun getMealByType(type: MealType): Flow<DataState<List<Meal>>> = flow {
        emit(DataState.Loading)
        try {
            val data = mealDao.getMealByType(type).map { it.asDomain() }
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: MealRepositoryImpl::getMealByType"))
        }
    }

    override fun deleteAll(): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        try {
            mealDao.deleteAll()
            mealDishDao.deleteAll()
            menuMealDao.deleteAll()
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: MealRepositoryImpl::deleteAll"))
        }
    }

    override fun delete(id: Int): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        try {
            mealDao.delete(id)
            mealDishDao.deleteByMeal(id)
            menuMealDao.deleteByMeal(id)
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: MealRepositoryImpl::delete"))
        }
    }


    override fun insert(meal: Meal): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        try {
            mealDao.insert(meal.asEntity())
            mealDishDao.insert(meal.getMealDish())
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: MealRepositoryImpl::insert"))
        }
    }

    override fun update(meal: Meal): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        try {
            mealDao.update(meal.asEntity())
            mealDishDao.deleteByMeal(meal.id)
            mealDishDao.insert(meal.getMealDish())
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: MealRepositoryImpl::update"))
        }
    }
}