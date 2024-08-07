package ru.tanexc.schoolmenu.presentation.screen.menu.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.tanexc.schoolmenu.core.util.DataState
import ru.tanexc.schoolmenu.domain.model.DayOfWeek
import ru.tanexc.schoolmenu.domain.model.Menu
import ru.tanexc.schoolmenu.domain.repository.MenuRepository


class MenuViewModel(
    private val menuRepository: MenuRepository
) : ViewModel() {
    val selectedDay: MutableStateFlow<DayOfWeek> = MutableStateFlow(DayOfWeek.Monday)

    private val _selectedMenu: MutableState<Menu?> = mutableStateOf(null)
    val selectedMenu by _selectedMenu

    private val _data: MutableState<List<Menu>> = mutableStateOf(emptyList())
    val data by _data

    private val _dayData: MutableState<List<Menu>> = mutableStateOf(emptyList())
    val dayData by _dayData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(300)
            menuRepository.getMenuForWeek().collect { state ->
                when (state) {
                    is DataState.Success -> {
                        _data.value = state.data
                        _selectedMenu.value = data.firstOrNull { it.dayOfWeek == selectedDay.value }
                    }
                    else -> {}
                }
            }
            collectDayMenu()
        }
    }


    private fun collectDayMenu() {
        viewModelScope.launch(Dispatchers.IO) {
            selectedDay.collectLatest { day ->
                menuRepository.getMenuByDay(day).collect { state ->
                    when (state) {
                        is DataState.Success -> {
                            _dayData.value = state.data
                            _selectedMenu.value = data.firstOrNull { it.dayOfWeek == selectedDay.value }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    fun selectDay(day: DayOfWeek) {
        selectedDay.value = day
    }

    fun setDayMenu(day: DayOfWeek, menuId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            menuRepository.setMenuForWeek(day.ordinal, menuId).collect { state ->
                when (state) {
                    is DataState.Success -> {
                        menuRepository.getMenuByDay(day).collect { st ->
                            when (st) {
                                is DataState.Success -> _dayData.value = st.data
                                else -> {}
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}