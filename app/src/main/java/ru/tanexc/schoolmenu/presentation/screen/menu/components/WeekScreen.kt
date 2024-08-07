package ru.tanexc.schoolmenu.presentation.screen.menu.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.koin.java.KoinJavaComponent.getKoin
import ru.tanexc.schoolmenu.R
import ru.tanexc.schoolmenu.domain.model.DayOfWeek
import ru.tanexc.schoolmenu.presentation.screen.menu.viewModel.MenuViewModel

@Composable
fun WeekScreen(
    modifier: Modifier,
    topPadding: Dp,
    onCreateMeal: (DayOfWeek) -> Unit
) {
    val menuSelectionMode = remember { mutableStateOf(false) }

    val menuViewModel: MenuViewModel by getKoin().inject()
    val selectedDay = menuViewModel.selectedDay.collectAsState()

    Box(modifier) {
        Column(Modifier.fillMaxSize()) {
            Column(
                Modifier.background(
                    colorScheme.primaryContainer.copy(if (isSystemInDarkTheme()) 0.3f else 0.8f),
                    RoundedCornerShape(0.dp, 0.dp, 22.dp, 22.dp)
                )
            ) {
                Row(
                    Modifier
                        .padding(0.dp, topPadding, 0.dp, 0.dp)
                        .height(84.dp)
                ) {
                    DayOfWeekCard(
                        selected = selectedDay.value,
                        onClick = { menuViewModel.selectDay(DayOfWeek.Monday) },
                        day = DayOfWeek.Monday,
                        text = "Mon"
                    )
                    DayOfWeekCard(
                        selected = selectedDay.value,
                        onClick = { menuViewModel.selectDay(DayOfWeek.Tuesday) },
                        day = DayOfWeek.Tuesday,
                        text = "Tue"
                    )
                    DayOfWeekCard(
                        selected = selectedDay.value,
                        onClick = { menuViewModel.selectDay(DayOfWeek.Wednesday) },
                        day = DayOfWeek.Wednesday,
                        text = "Wed"
                    )
                    DayOfWeekCard(
                        selected = selectedDay.value,
                        onClick = { menuViewModel.selectDay(DayOfWeek.Thursday) },
                        day = DayOfWeek.Thursday,
                        text = "Thu"
                    )
                    DayOfWeekCard(
                        selected = selectedDay.value,
                        onClick = { menuViewModel.selectDay(DayOfWeek.Friday) },
                        day = DayOfWeek.Friday,
                        text = "Fri"
                    )
                    DayOfWeekCard(
                        selected = selectedDay.value,
                        onClick = { menuViewModel.selectDay(DayOfWeek.Saturday) },
                        day = DayOfWeek.Saturday,
                        text = "Sat"
                    )
                    DayOfWeekCard(
                        selected = selectedDay.value,
                        onClick = { menuViewModel.selectDay(DayOfWeek.Sunday) },
                        day = DayOfWeek.Sunday,
                        text = "Sun"
                    )
                    DayOfWeekCard(
                        selected = selectedDay.value,
                        onClick = { menuViewModel.selectDay(DayOfWeek.Any) },
                        day = DayOfWeek.Any,
                        text = "All"
                    )
                }

                menuViewModel.selectedMenu?.let { menu ->
                    MenuCard(
                        menu = menu,
                        onMealClick = { /*TODO*/ }
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                stringResource(R.string.cost) + ": ${menu.meals.sumOf { it.cost.toDouble() }} " + stringResource(
                                    R.string.rub
                                )
                            )
                        }
                    }
                } ?: if (selectedDay.value != DayOfWeek.Any) {
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (!menuSelectionMode.value) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Меню на ${selectedDay.value.text} не выбрано",
                                    color = colorScheme.onPrimaryContainer
                                )
                                OutlinedButton(
                                    onClick = { menuSelectionMode.value = true },
                                    Modifier.padding(8.dp)
                                ) {
                                    Text(stringResource(R.string.select))
                                }
                            }
                        } else {
                            val infiniteTransition =
                                rememberInfiniteTransition(label = "infinite transition")
                            val scale by infiniteTransition.animateFloat(
                                initialValue = 1f,
                                targetValue = 1.3f,
                                animationSpec = infiniteRepeatable(tween(800), RepeatMode.Reverse),
                                label = "scale"
                            )
                            Column(
                                modifier = Modifier
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    stringResource(R.string.selectmenu),
                                    color = colorScheme.onPrimaryContainer
                                )

                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null,
                                    Modifier
                                        .padding(16.dp)
                                        .graphicsLayer {
                                            scaleX = scale
                                            scaleY = scale
                                            transformOrigin = TransformOrigin.Center
                                        }
                                )
                            }
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
            LazyColumn(Modifier.fillMaxSize()) {
                items(menuViewModel.dayData) { data ->
                    MenuCard(menu = data, onMealClick = { /*TODO*/ }) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                stringResource(R.string.cost) + ": ${data.meals.sumOf { it.cost.toDouble() }} " + stringResource(
                                    R.string.rub
                                )
                            )

                            if (menuSelectionMode.value) {
                                Button(onClick = {
                                    menuViewModel.setDayMenu(
                                        selectedDay.value,
                                        data.id
                                    )
                                }) {
                                    Text(stringResource(R.string.select))
                                }
                            }
                        }
                    }
                }
            }
        }
        Column(
            Modifier
                .align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.End,
        ) {
            FloatingActionButton(
                onClick = {
                    onCreateMeal(selectedDay.value)
                },
                shape = FloatingActionButtonDefaults.smallShape,
                containerColor = colorScheme.secondaryContainer,
                contentColor = contentColorFor(backgroundColor = colorScheme.secondaryContainer),
                modifier = Modifier
                    .padding(16.dp, 0.dp)
                    .size(32.dp)
            ) {
                Icon(
                    Icons.Outlined.Add,
                    contentDescription = null,
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    )
                )
            }
            FloatingActionButton(
                onClick = {
                    menuSelectionMode.value = true
                },
                containerColor = colorScheme.secondaryContainer,
                contentColor = contentColorFor(backgroundColor = colorScheme.secondaryContainer),
                modifier = Modifier
                    .padding(16.dp)
                    .size(56.dp)
            ) {
                Icon(
                    Icons.Outlined.Edit,
                    contentDescription = null,
                    modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    )
                )
            }
        }
    }
}