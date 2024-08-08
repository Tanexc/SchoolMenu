package ru.tanexc.schoolmenu.presentation.screen.dish

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.mp.KoinPlatform.getKoin
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.presentation.screen.dish.components.DishInfo
import ru.tanexc.schoolmenu.presentation.screen.dish.components.DishList

@Composable
fun DishScreen(
    modifier: Modifier,
    changeBottomBar: (Boolean) -> Unit
) {
    val viewModel: DishViewModel by getKoin().inject()
    BackHandler {
        if (viewModel.selectedDish != null) {
            viewModel.selectDish(null)
        }
    }

    when (val dish = viewModel.selectedDish) {
        is Dish -> {
            changeBottomBar(false)
            Column(modifier.fillMaxSize()) {
                DishInfo(viewModel, dish)
            }
        }

        else -> {
            changeBottomBar(true)
            Column(modifier) {
                DishList(viewModel)
            }
        }
    }
}