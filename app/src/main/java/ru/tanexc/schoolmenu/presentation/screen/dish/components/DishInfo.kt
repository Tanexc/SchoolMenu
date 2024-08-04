package ru.tanexc.schoolmenu.presentation.screen.dish.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.tanexc.schoolmenu.R
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.presentation.screen.dish.DishViewModel
import ru.tanexc.schoolmenu.presentation.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishInfo(
    viewModel: DishViewModel,
    dish: Dish
) {
    val editDialogVisibility = remember { mutableStateOf(false) }
    val deleteDialogVisibility = remember { mutableStateOf(false) }
    val scaffoldState = rememberBottomSheetScaffoldState()

    LaunchedEffect(editDialogVisibility) {
        if (editDialogVisibility.value) {
            scaffoldState.bottomSheetState.show()
        } else scaffoldState.bottomSheetState.hide()
    }

    BottomSheetScaffold(
        sheetContent = {
            EditDishScreen(
                dish = dish,
                onClose = {
                    editDialogVisibility.value = false
                },
                onSubmit = { upd ->
                    viewModel.updateDish(upd)
                    editDialogVisibility.value = false
                }
            )
        },
        scaffoldState = scaffoldState
    ) {

        TopAppBar(
            title = {
                Text(dish.title)
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
                    onClick = { editDialogVisibility.value = true }
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
                Spacer(modifier = Modifier.size(8.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            MaterialTheme.colorScheme.secondary.copy(0.6f),
                            RoundedCornerShape(22.dp)
                        )
                ) {
                    AsyncImage(
                        model = dish.image,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(22.dp))
                    )
                    Text(
                        dish.title,
                        Modifier
                            .fillMaxWidth()
                            .padding(22.dp),
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        fontStyle = Typography.headlineMedium.copy(fontWeight = FontWeight.Bold).fontStyle
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.size(8.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            MaterialTheme.colorScheme.secondary.copy(0.6f),
                            RoundedCornerShape(22.dp)
                        )
                ) {
                    Text(
                        stringResource(R.string.harm),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        dish.harm.harm,
                        Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.size(8.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            MaterialTheme.colorScheme.secondary.copy(0.6f),
                            RoundedCornerShape(22.dp)
                        )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(22.dp, 22.dp, 22.dp, 8.dp)
                    ) {
                        Text(stringResource(R.string.cost), fontWeight = FontWeight.Bold)
                        Text("${dish.cost} " + stringResource(R.string.rub))
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(22.dp, 8.dp, 22.dp, 8.dp)
                    ) {
                        Text(stringResource(R.string.weight), fontWeight = FontWeight.Bold)
                        Text("${dish.weight} " + stringResource(R.string.gr))
                    }

                }
                Spacer(modifier = Modifier.size(8.dp))
            }

            item {
                Spacer(modifier = Modifier.size(8.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            MaterialTheme.colorScheme.secondary.copy(0.6f),
                            RoundedCornerShape(22.dp)
                        )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(22.dp, 22.dp, 22.dp, 8.dp)
                    ) {
                        Text(stringResource(R.string.protein), fontWeight = FontWeight.Bold)
                        Text("${dish.protein} " + stringResource(R.string.gr))
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(22.dp, 8.dp, 22.dp, 8.dp)
                    ) {
                        Text(stringResource(R.string.fats), fontWeight = FontWeight.Bold)
                        Text("${dish.fats} " + stringResource(R.string.gr))
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(22.dp, 8.dp, 22.dp, 8.dp)
                    ) {
                        Text(stringResource(R.string.carbon), fontWeight = FontWeight.Bold)
                        Text("${dish.carbon} " + stringResource(R.string.gr))
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(22.dp, 8.dp, 22.dp, 22.dp)
                    ) {
                        Text(stringResource(R.string.calories), fontWeight = FontWeight.Bold)
                        Text("${dish.calories} " + stringResource(R.string.ccal))
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
    AnimatedVisibility(visible = deleteDialogVisibility.value) {
        AlertDialog(
            onDismissRequest = { deleteDialogVisibility.value = false },
            confirmButton = {
                viewModel.deleteDish(dish.id)
                deleteDialogVisibility.value = false
            },
            icon = { Icon(Icons.Outlined.Delete, null) },
            title = { Text(stringResource(R.string.deleteTitle)) },
            text = { Text(stringResource(R.string.deleteText)) }
        )
    }
}