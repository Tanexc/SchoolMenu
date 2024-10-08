package ru.tanexc.schoolmenu.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.tanexc.schoolmenu.core.util.DataState
import ru.tanexc.schoolmenu.domain.model.DayOfWeek
import ru.tanexc.schoolmenu.domain.model.Menu

interface MenuRepository {
    fun getAllMenu(page: Int): Flow<DataState<List<Menu>>>

    fun getMenuByDay(day: DayOfWeek): Flow<DataState<List<Menu>>>

    fun deleteAll(): Flow<DataState<Unit>>

    fun delete(id: Int): Flow<DataState<Unit>>

    fun insert(menu: Menu): Flow<DataState<Unit>>

    fun update(menu: Menu): Flow<DataState<Unit>>

    fun getMenuForWeek(): Flow<DataState<List<Menu>>>

    fun setMenuForWeek(day: Int, menuId: Int): Flow<DataState<Unit>>
}