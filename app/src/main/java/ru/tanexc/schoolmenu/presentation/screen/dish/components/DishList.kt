package ru.tanexc.schoolmenu.presentation.screen.dish.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import ru.tanexc.schoolmenu.R
import ru.tanexc.schoolmenu.core.util.UIState
import ru.tanexc.schoolmenu.presentation.screen.dish.DishViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishList(
    viewModel: DishViewModel
) {
    val scrollState = rememberLazyListState()
    val sheetState = rememberModalBottomSheetState()
    val bottomSheetVisibility = remember { mutableStateOf(false) }

    LaunchedEffect(bottomSheetVisibility.value) {
        delay(100)
        if (bottomSheetVisibility.value) sheetState.expand()
        else sheetState.hide()
    }

    Box(Modifier.fillMaxSize()) {
        when (val state = viewModel.uiState) {
            is UIState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(32.dp)
                )
            }

            is UIState.Error -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                        .background(
                            colorScheme.tertiaryContainer.copy(0.6f),
                            RoundedCornerShape(16.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.ErrorOutline, null,
                        Modifier
                            .padding(16.dp)
                            .size(44.dp))
                    Text(state.message, Modifier.padding(16.dp, 0.dp, 16.dp, 16.dp), textAlign = TextAlign.Center)
                }
            }

            is UIState.Success -> {
                Column(Modifier.fillMaxSize()) {
                    CenterAlignedTopAppBar(
                        title = {
                            if (viewModel.searchMode) {
                                TextField(
                                    value = viewModel.search,
                                    onValueChange = viewModel::updateSearch,
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = TextFieldDefaults.colors().copy(
                                        focusedContainerColor = Color.Transparent,
                                        errorContainerColor = Color.Transparent,
                                        disabledContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent
                                    ),
                                    placeholder = { Text(stringResource(R.string.title)) }
                                )
                            } else {
                                Text(
                                    stringResource(R.string.dishlist)
                                )
                            }
                        },
                        actions = {
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
                    )
                    LazyColumn(
                        Modifier.fillMaxSize(),
                        state = scrollState
                    ) {
                        itemsIndexed(viewModel.data) { index, dish ->
                            DishCard(dish, onClick = { viewModel.selectDish(dish) })
                            if (index < viewModel.data.lastIndex) {
                                Spacer(modifier = Modifier.size(12.dp))
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
                        bottomSheetVisibility.value = true
                    },
                    containerColor = colorScheme.secondaryContainer,
                    contentColor = contentColorFor(backgroundColor = colorScheme.secondaryContainer),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .height(56.dp)
                ) {
                    Icon(
                        Icons.Outlined.Add,
                        contentDescription = null,
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }
            }
        }

        if (bottomSheetVisibility.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    bottomSheetVisibility.value = false
                },
                sheetState = sheetState
            ) {
                EditDishScreen(
                    onClose = {
                        bottomSheetVisibility.value = false
                    },
                    onSubmit = {
                        bottomSheetVisibility.value = false
                        viewModel.createDish(it)
                    },
                    onFocus = { }
                )
            }
        }
    }
}