package ru.tanexc.schoolmenu.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tanexc.schoolmenu.core.util.DataState
import ru.tanexc.schoolmenu.data.local.dao.MenuDao
import ru.tanexc.schoolmenu.data.local.dao.MenuMealDao
import ru.tanexc.schoolmenu.domain.model.DayOfWeek
import ru.tanexc.schoolmenu.domain.model.Menu

class MenuRepositoryImpl(
    private val menuDao: MenuDao,
    private val menuMealDao: MenuMealDao
): MenuRepository {
    override fun getAllMenu(page: Int): Flow<DataState<List<Menu>>> = flow {
        emit(DataState.Loading)
        try {
            val data = menuDao.getAllMenu(20, page * 20).map { it.asDomain() }
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: MenuRepository::getAllMenu"))
        }
    }

    override fun getMenuByDay(day: DayOfWeek): Flow<DataState<List<Menu>>> = flow {
        emit(DataState.Loading)
        try {
            val data = menuDao.getMenuByDay(day).map { it.asDomain() }
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: MenuRepository::getMenuByDay"))
        }
    }

    override fun deleteAll(): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        try {
            menuDao.deleteAll()
            menuMealDao.deleteAll()
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: MenuRepository::deleteAll"))
        }
    }

    override fun delete(id: Int): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        try {
            menuDao.delete(id)
            menuMealDao.deleteByMenu(id)
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: MenuRepository::delete"))
        }
    }

    override fun insert(menu: Menu): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        try {
            menuDao.insert(menu.asEntity())
            menuMealDao.insert(menu.getMenuMeal())
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: MenuRepository::insert"))
        }
    }

    override fun update(menu: Menu): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        try {
            menuDao.insert(menu.asEntity())
            menuMealDao.deleteByMenu(menu.id)
            menuMealDao.insert(menu.getMenuMeal())
            emit(DataState.Success(Unit))
        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "error: MenuRepository::update"))
        }
    }
}