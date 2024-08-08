package ru.tanexc.schoolmenu.presentation.screen.dish

import android.util.Log
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
import ru.tanexc.schoolmenu.core.util.UIState
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.domain.repository.DishRepository

class DishViewModel(
    private val dishRepository: DishRepository
) : ViewModel() {
    private val _selectedDish: MutableState<Dish?> = mutableStateOf(null)
    val selectedDish by _selectedDish

    private val _data: MutableState<List<Dish>> = mutableStateOf(emptyList())
    val data by _data

    private val _search: MutableState<String> = mutableStateOf("")
    val search by _search

    private val _searchMode: MutableState<Boolean> = mutableStateOf(false)
    val searchMode by _searchMode

    private val page: MutableStateFlow<Int> = MutableStateFlow(0)

    private val _uiState: MutableState<UIState> = mutableStateOf(UIState.Loading)
    val uiState by _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(400)
            collectPage()
        }
    }

    fun nextPage() {
        if (_uiState.value != UIState.Loading) page.value++
        _uiState.value = UIState.Loading
    }

    fun updateSearchMode(value: Boolean) {
        _searchMode.value = value
    }

    fun selectDish(dish: Dish?) {
        _selectedDish.value = dish
    }

    fun createDish(dish: Dish) {
        viewModelScope.launch(Dispatchers.IO) {
            dishRepository.insertDish(dish).collect { state ->
                when (state) {
                    is DataState.Success -> {
                        _uiState.value = UIState.Success
                        _data.value = emptyList()
                        page.value = -1
                        dishRepository.getByTitle(dish.title).collect { st ->
                            when (st) {
                                is DataState.Success -> {
                                    _selectedDish.value = st.data.first()
                                }

                                is DataState.Loading -> {
                                    _uiState.value = UIState.Loading
                                }

                                is DataState.Error -> {
                                    _uiState.value = UIState.Error(st.message)
                                }
                            }
                        }
                    }

                    is DataState.Loading -> {
                        _uiState.value = UIState.Loading
                    }

                    is DataState.Error -> {
                        _uiState.value = UIState.Error(state.message)
                        _selectedDish.value = null
                    }
                }
            }
        }
    }

    fun updateSearch(searchString: String) {
        _search.value = searchString
        viewModelScope.launch(Dispatchers.IO) {
            dishRepository.getByTitle(searchString).collect { state ->
                when (state) {
                    is DataState.Success -> {
                        _data.value = state.data
                        _uiState.value = UIState.Success
                    }

                    is DataState.Loading -> {
                        _uiState.value = UIState.Loading
                    }

                    is DataState.Error -> {
                        _uiState.value = UIState.Error(state.message)
                    }
                }
            }
        }
    }

    fun updateDish(dish: Dish) {
        viewModelScope.launch(Dispatchers.IO) {
            dishRepository.updateDish(dish).collect { state ->
                when (state) {
                    is DataState.Success -> {
                        _data.value = _data.value.map { if (it.id == dish.id) dish else it }
                        _selectedDish.value = dish
                        _uiState.value = UIState.Success
                    }

                    is DataState.Loading -> {
                        _uiState.value = UIState.Loading
                    }

                    else -> {}
                }
            }
        }
    }

    fun deleteDish(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dishRepository.delete(id).collect { state ->
                when (state) {
                    is DataState.Success -> {
                        _data.value = _data.value.filter { it.id != id }
                        _uiState.value = UIState.Success
                    }

                    is DataState.Loading -> {
                        _uiState.value = UIState.Loading
                    }

                    else -> {}
                }
            }
        }
    }

    fun closeSearch() {
        _searchMode.value = false
        _search.value = ""
        _data.value = emptyList()
        _uiState.value = UIState.Loading
        page.value = -1
    }

    private suspend fun collectPage() {
        page.collectLatest { pg ->
            if (page.value < 0) page.value = 0
            dishRepository.getAllDishes(pg).collect { state ->
                when (state) {
                    is DataState.Success -> {
                        _data.value += state.data
                        _uiState.value = UIState.Success
                    }

                    is DataState.Loading -> {
                        _uiState.value = UIState.Loading
                    }

                    is DataState.Error -> {
                        _uiState.value = UIState.Error(state.message)
                    }
                }
            }
        }
    }

}