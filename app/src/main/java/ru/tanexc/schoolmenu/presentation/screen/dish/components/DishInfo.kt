package ru.tanexc.schoolmenu.presentation.screen.dish.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.tanexc.schoolmenu.R
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.presentation.screen.dish.DishViewModel
import ru.tanexc.schoolmenu.presentation.screen.dish.components.EditDishScreen
import ru.tanexc.schoolmenu.presentation.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishInfo(
    viewModel: DishViewModel,
    dish: Dish
) {
    val currentDish = remember { mutableStateOf(dish) }
    val sheetVisibility = remember { mutableStateOf(false) }
    val deleteDialogVisibility = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(sheetVisibility.value) {
        if (sheetVisibility.value) sheetState.show()
        else sheetState.hide()
    }

    Column{
        CenterAlignedTopAppBar(
            title = {
                Text(currentDish.value.title, fontWeight = FontWeight.Bold)
            },
            navigationIcon = {
                IconButton(
                    onClick = { viewModel.selectDish(null) }
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        null
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = { sheetVisibility.value = true }
                ) {
                    Icon(
                        Icons.Default.Edit,
                        null
                    )
                }
                IconButton(
                    onClick = { deleteDialogVisibility.value = true }
                ) {
                    Icon(
                        Icons.Outlined.Delete,
                        null
                    )
                }
            }
        )
        LazyColumn(Modifier.fillMaxSize()) {
            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 0.dp)
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer.copy(0.8f),
                            RoundedCornerShape(22.dp)
                        )
                ) {
                    Image(
                        currentDish.value.image,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(22.dp))
                    )
                    Text(
                        currentDish.value.title,
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
                        .padding(8.dp, 4.dp)
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer.copy(0.8f),
                            RoundedCornerShape(22.dp)
                        )
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(22.dp)) {
                        Text(
                            stringResource(R.string.harm),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            currentDish.value.harm.harm,
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
                        .padding(8.dp, 0.dp)
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer.copy(0.8f),
                            RoundedCornerShape(22.dp)
                        )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(22.dp, 22.dp, 22.dp, 8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.cost), fontWeight = FontWeight.Bold)
                        Text("${currentDish.value.cost} " + stringResource(R.string.rub))
                    }
                    Spacer(modifier = Modifier.fillMaxWidth().padding(22.dp, 0.dp).height(0.75.dp).background(MaterialTheme.colorScheme.outline.copy(0.4f), RoundedCornerShape(50)))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(22.dp, 8.dp, 22.dp, 22.dp)
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.weight), fontWeight = FontWeight.Bold)
                        Text("${currentDish.value.weight} " + stringResource(R.string.gr))
                    }

                }
            }

            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 4.dp)
                        .background(
                            MaterialTheme.colorScheme.secondaryContainer.copy(0.8f),
                            RoundedCornerShape(22.dp)
                        )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(22.dp, 22.dp, 22.dp, 8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.protein), fontWeight = FontWeight.Bold)
                        Text("${currentDish.value.protein} " + stringResource(R.string.gr))
                    }
                    Spacer(modifier = Modifier.fillMaxWidth().padding(22.dp, 0.dp).height(0.75.dp).background(MaterialTheme.colorScheme.outline.copy(0.4f), RoundedCornerShape(50)))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(22.dp, 8.dp, 22.dp, 8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.fats), fontWeight = FontWeight.Bold)
                        Text("${currentDish.value.fats} " + stringResource(R.string.gr))
                    }
                    Spacer(modifier = Modifier.fillMaxWidth().padding(22.dp, 0.dp).height(0.75.dp).background(MaterialTheme.colorScheme.outline.copy(0.4f), RoundedCornerShape(50)))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(22.dp, 8.dp, 22.dp, 8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.carbon), fontWeight = FontWeight.Bold)
                        Text("${currentDish.value.carbon} " + stringResource(R.string.gr))
                    }
                    Spacer(modifier = Modifier.fillMaxWidth().padding(22.dp, 0.dp).height(0.75.dp).background(MaterialTheme.colorScheme.outline.copy(0.4f), RoundedCornerShape(50)))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(22.dp, 8.dp, 22.dp, 22.dp)
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.calories), fontWeight = FontWeight.Bold)
                        Text("${currentDish.value.calories} " + stringResource(R.string.ccal))
                    }
                }
            }
        }
    }
    if (sheetVisibility.value) {
        ModalBottomSheet(
            onDismissRequest = {
                sheetVisibility.value = false
            },
            sheetState = sheetState
        ) {
            EditDishScreen(
                dish = currentDish.value,
                onClose = {
                    sheetVisibility.value = false
                },
                onSubmit = {
                    sheetVisibility.value = false
                    viewModel.updateDish(it)
                    currentDish.value = it
                }
            )
        }
    }
    AnimatedVisibility(visible = deleteDialogVisibility.value) {
        AlertDialog(
            onDismissRequest = { deleteDialogVisibility.value = false },
            confirmButton = {
                viewModel.deleteDish(currentDish.value.id)
                deleteDialogVisibility.value = false
            },
            icon = { Icon(Icons.Outlined.Delete, null) },
            title = { Text(stringResource(R.string.deleteTitle)) },
            text = { Text(stringResource(R.string.deleteText)) }
        )
    }
}