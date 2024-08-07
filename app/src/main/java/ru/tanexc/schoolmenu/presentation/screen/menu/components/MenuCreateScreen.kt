package ru.tanexc.schoolmenu.presentation.screen.menu.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.java.KoinJavaComponent.getKoin
import ru.tanexc.schoolmenu.R
import ru.tanexc.schoolmenu.domain.model.DayOfWeek
import ru.tanexc.schoolmenu.presentation.screen.menu.viewModel.MenuCreateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuCreateScreen(
    onClose: () -> Unit,
    dayOfWeek: DayOfWeek
) {
    val viewModel: MenuCreateViewModel by getKoin().inject()
    val restrictCost = remember { mutableStateOf(false) }
    val cost = remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()
    val sheetState = rememberModalBottomSheetState()
    val selectedIndex = remember { mutableIntStateOf(0) }

    LaunchedEffect(viewModel.selectedDish) {
        if (viewModel.selectedDish != null) {
            sheetState.partialExpand()
        } else {
            sheetState.hide()
        }
    }

    Box(Modifier.fillMaxSize()) {
        Column {
            CenterAlignedTopAppBar(
                title = { Text(dayOfWeek.full) },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
            PrimaryTabRow(selectedTabIndex = selectedIndex.intValue) {
                viewModel.meal.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedIndex.intValue,
                        onClick = {
                            selectedIndex.intValue = index
                            viewModel.selectMeal(index)
                        },
                        text = {
                            Text(
                                text = item.type.title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(16.dp)
            ) {
                Checkbox(
                    checked = restrictCost.value,
                    onCheckedChange = {
                        restrictCost.value = it
                        if (restrictCost.value) {
                            viewModel.updateMeal(cost = viewModel.selectedMeal.dishes.sumOf { d -> d.cost.toDouble() }.toFloat())
                        }
                    })
                Text(stringResource(R.string.cost_by_dishes))
            }
            OutlinedTextField(
                enabled = !restrictCost.value,
                value = if (!restrictCost.value) cost.value else viewModel.selectedMeal.cost.toString(),
                onValueChange = {
                    if (it.filter { ch -> !"0123456789.".contains(ch) } == "")
                        cost.value = it
                    viewModel.updateMeal(
                        cost = if (!restrictCost.value) cost.value.toFloatOrNull()
                            ?: 0f else viewModel.selectedMeal.cost
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 16.dp),
                label = { Text(stringResource(R.string.cost)) },
                placeholder = { Text(stringResource(R.string.cost)) },
                suffix = { Text(stringResource(R.string.rub)) }
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
                if (viewModel.searchMode) {
                    TextField(
                        value = viewModel.search,
                        onValueChange = viewModel::updateSearch,
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        colors = TextFieldDefaults.colors().copy(
                            focusedContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        placeholder = { Text(stringResource(R.string.title)) },
                        maxLines = 2
                    )
                }
                if (viewModel.searchMode) {
                    IconButton(onClick = { viewModel.closeSearch() }) {
                        Icon(Icons.Default.Close, null)
                    }
                } else {
                    IconButton(onClick = { viewModel.updateSearchMode(true) }) {
                        Icon(Icons.Default.Search, null)
                    }
                }
            }
            LazyColumn(
                Modifier
                    .padding(16.dp, 0.dp)
            ) {
                items(viewModel.selectedMeal.dishes) { dish ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = viewModel.selectedMeal.dishes.contains(dish),
                            onCheckedChange = {
                                val dishes = viewModel.selectedMeal.dishes.filter { d -> d.id != dish.id }
                                viewModel.updateMeal(
                                    index = selectedIndex.intValue,
                                    dishes = dishes,
                                    cost = if (!restrictCost.value) cost.value.toFloatOrNull()
                                        ?: 0f else dishes.sumOf { t -> t.cost.toDouble() }
                                        .toFloat(),
                                )
                            }
                        )
                        Text(dish.title)
                        Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
                        IconButton(onClick = { viewModel.selectDish(dish) }) {
                            Icon(Icons.Outlined.Info, null)
                        }
                    }
                }
            }
            HorizontalDivider()
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp, 0.dp),
                scrollState
            ) {
                items(viewModel.data.filter { !viewModel.selectedMeal.dishes.contains(it) }) { dish ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = viewModel.selectedMeal.dishes.contains(dish),
                            onCheckedChange = {

                                val dishes = if (it) {
                                    viewModel.selectedMeal.dishes + dish
                                } else {
                                    viewModel.selectedMeal.dishes.filter { d -> d.id != dish.id }
                                }
                                viewModel.updateMeal(
                                    index = selectedIndex.intValue,
                                    dishes = dishes,
                                    cost = if (!restrictCost.value) cost.value.toFloatOrNull()
                                        ?: 0f else dishes.sumOf { t -> t.cost.toDouble() }
                                        .toFloat(),
                                )
                            }
                        )
                        Text(dish.title)
                        Spacer(modifier = Modifier.fillMaxWidth().weight(1f))
                        IconButton(onClick = { viewModel.selectDish(dish) }) {
                            Icon(Icons.Outlined.Info, null)
                        }
                    }
                }
                item {
                    LaunchedEffect(true) {
                        if (scrollState.canScrollBackward)
                            viewModel.nextPage()
                    }
                }
                item {
                    Spacer(Modifier.size(32.dp))
                }
            }
        }
        FloatingActionButton(
            onClick = {
                viewModel.saveMenu(dayOfWeek)
            },
            containerColor = colorScheme.secondaryContainer,
            contentColor = contentColorFor(backgroundColor = colorScheme.secondaryContainer),
            modifier = Modifier
                .padding(16.dp)
                .size(56.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                Icons.Outlined.Edit,
                contentDescription = null,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }
    if (viewModel.selectedDish != null) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.selectDish(null) },
            sheetState = sheetState
        ) {
            viewModel.selectedDish?.let { currentDish ->
                Column(Modifier.fillMaxSize()) {
                    LazyColumn(Modifier.fillMaxSize()) {
                        item {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .background(
                                        colorScheme.secondaryContainer.copy(0.8f),
                                        RoundedCornerShape(22.dp)
                                    )
                            ) {
                                Image(
                                    currentDish.image,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(22.dp))
                                )
                                Text(
                                    currentDish.title,
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(22.dp),
                                    fontSize = 28.sp,
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        item {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 4.dp)
                                    .background(
                                        colorScheme.secondaryContainer.copy(0.8f),
                                        RoundedCornerShape(22.dp)
                                    )
                            ) {
                                Column(
                                    Modifier
                                        .fillMaxSize()
                                        .padding(22.dp)
                                ) {
                                    Text(
                                        stringResource(R.string.harm),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        currentDish.harm.harm,
                                        Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }

                            }
                        }

                        item {
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .background(
                                        colorScheme.secondaryContainer.copy(0.8f),
                                        RoundedCornerShape(22.dp)
                                    )
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .padding(22.dp, 22.dp, 22.dp, 8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        stringResource(R.string.cost),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text("${currentDish.cost} " + stringResource(R.string.rub))
                                }
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(22.dp, 0.dp)
                                        .height(0.75.dp)
                                        .background(
                                            colorScheme.outline.copy(0.4f),
                                            RoundedCornerShape(50)
                                        )
                                )
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .padding(22.dp, 8.dp, 22.dp, 22.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        stringResource(R.string.weight),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text("${currentDish.weight} " + stringResource(R.string.gr))
                                }

                            }
                        }

                        item {
                            Spacer(modifier = Modifier.size(4.dp))
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .background(
                                        colorScheme.secondaryContainer.copy(0.8f),
                                        RoundedCornerShape(22.dp)
                                    )
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .padding(22.dp, 22.dp, 22.dp, 8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        stringResource(R.string.protein),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text("${currentDish.protein} " + stringResource(R.string.gr))
                                }
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(22.dp, 0.dp)
                                        .height(0.75.dp)
                                        .background(
                                            colorScheme.outline.copy(0.4f),
                                            RoundedCornerShape(50)
                                        )
                                )
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .padding(22.dp, 8.dp, 22.dp, 8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        stringResource(R.string.fats),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text("${currentDish.fats} " + stringResource(R.string.gr))
                                }
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(22.dp, 0.dp)
                                        .height(0.75.dp)
                                        .background(
                                            colorScheme.outline.copy(0.4f),
                                            RoundedCornerShape(50)
                                        )
                                )
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .padding(22.dp, 8.dp, 22.dp, 8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        stringResource(R.string.carbon),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text("${currentDish.carbon} " + stringResource(R.string.gr))
                                }
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(22.dp, 0.dp)
                                        .height(0.75.dp)
                                        .background(
                                            colorScheme.outline.copy(0.4f),
                                            RoundedCornerShape(50)
                                        )
                                )
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .padding(22.dp, 8.dp, 22.dp, 22.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        stringResource(R.string.calories),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text("${currentDish.calories} " + stringResource(R.string.ccal))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}