package ru.tanexc.schoolmenu.presentation.screen.meal

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.tanexc.schoolmenu.core.util.DataState
import ru.tanexc.schoolmenu.core.util.UIState
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.domain.model.Meal
import ru.tanexc.schoolmenu.domain.model.MealType
import ru.tanexc.schoolmenu.domain.repository.DishRepository
import ru.tanexc.schoolmenu.domain.repository.MealRepository

class CreateMealViewModel(
    private val mealRepository: MealRepository,
    private val dishRepository: DishRepository
) : ViewModel() {
    private val _selectedDish: MutableState<Dish?> = mutableStateOf(null)
    val selectedDish by _selectedDish

    private val _searchMode: MutableState<Boolean> = mutableStateOf(false)
    val searchMode by _searchMode

    private val _data: MutableState<List<Dish>> = mutableStateOf(emptyList())
    val data by _data

    private val page: MutableStateFlow<Int> = MutableStateFlow(0)

    private val _search: MutableState<String> = mutableStateOf("")
    val search by _search

    private val _uiState: MutableState<UIState> = mutableStateOf(UIState.Loading)
    val uiState by _uiState

    private val _meal: MutableState<Meal> = mutableStateOf(Meal.default())
    val meal by _meal


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

    fun updateMeal(
        cost: Float = meal.cost,
        mealType: MealType = meal.type,
        dishes: List<Dish> = meal.dishes
    ) {
        _meal.value = meal.copy(cost = cost, type = mealType, dishes = dishes)
    }

    fun saveMeal() {
        viewModelScope.launch(Dispatchers.IO) {
            mealRepository.insert(meal).collect { state ->
                when (state) {
                    is DataState.Success -> {
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
                Log.i("cum", "${_uiState.value}")
            }
        }
    }
}