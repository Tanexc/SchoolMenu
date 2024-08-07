package ru.tanexc.schoolmenu.presentation.screen.menu.viewModel

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
import ru.tanexc.schoolmenu.domain.model.DayOfWeek
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.domain.model.Meal
import ru.tanexc.schoolmenu.domain.model.MealType
import ru.tanexc.schoolmenu.domain.model.Menu
import ru.tanexc.schoolmenu.domain.repository.DishRepository
import ru.tanexc.schoolmenu.domain.repository.MealRepository
import ru.tanexc.schoolmenu.domain.repository.MenuRepository

class MenuCreateViewModel(
    private val mealRepository: MealRepository,
    private val dishRepository: DishRepository,
    private val menuRepository: MenuRepository
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

    private val _meal: MutableState<Array<Meal>> = mutableStateOf(
        arrayOf(
            Meal.default(type = MealType.BreakFast),
            Meal.default(type = MealType.Lunch),
            Meal.default(type = MealType.Snack),
            Meal.default()
        )
    )
    val meal by _meal

    private val _selectedMeal: MutableState<Meal> = mutableStateOf(meal[0])
    val selectedMeal by _selectedMeal


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

    fun selectMeal(index: Int) {
        _selectedMeal.value = meal[index]
    }

    fun updateMeal(
        index: Int = _selectedMeal.value.type.ordinal,
        cost: Float = _selectedMeal.value.cost,
        mealType: MealType = _selectedMeal.value.type,
        dishes: List<Dish> = _selectedMeal.value.dishes
    ) {
        _meal.value[index] = meal[index].copy(cost = cost, type = mealType, dishes = dishes)
        _selectedMeal.value = meal[index]
    }

    fun saveMenu(day: DayOfWeek) {
        viewModelScope.launch(Dispatchers.IO) {
            meal.forEach { m ->
                if (m.dishes.isNotEmpty()) {
                    mealRepository.insert(m).collect { state ->
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
            menuRepository.insert(
                Menu(
                    id = 0,
                    dayOfWeek = day,
                    meals = meal.toList()
                )
            )
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
            }
        }
    }


}