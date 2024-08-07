package ru.tanexc.schoolmenu.presentation.screen.dish

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.mp.KoinPlatform.getKoin
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.presentation.screen.dish.components.DishInfo
import ru.tanexc.schoolmenu.presentation.screen.dish.components.DishList

@Composable
fun DishScreen(modifier: Modifier) {
    val viewModel: DishViewModel by getKoin().inject()

    Column(modifier) {
        when(val dish = viewModel.selectedDish) {
            is Dish -> DishInfo(viewModel, dish)
            else -> DishList(viewModel)
        }
    }
}