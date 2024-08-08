package ru.tanexc.schoolmenu.presentation.screen.menu

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import ru.tanexc.schoolmenu.domain.model.DayOfWeek
import ru.tanexc.schoolmenu.presentation.screen.menu.components.MenuCreateScreen
import ru.tanexc.schoolmenu.presentation.screen.menu.components.WeekScreen

@Composable
fun MenuScreen(
    modifier: Modifier,
    topPadding: Dp,
    changeBottomBar: (Boolean) -> Unit
) {
    val dayOfWeek = remember { mutableStateOf<DayOfWeek?>(null) }

    BackHandler {
        if (dayOfWeek.value != null) {
            dayOfWeek.value = null
        }
    }

    when (val day = dayOfWeek.value) {
        null -> {

            WeekScreen(
                modifier = modifier, topPadding = topPadding, onCreateMeal = { dayOfWeek.value = it })
        }

        else -> {
            MenuCreateScreen(
                onClose = { dayOfWeek.value = null },
                dayOfWeek = day
            )
        }
    }

}