package ru.tanexc.schoolmenu.presentation.screen.dish.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tanexc.schoolmenu.R
import ru.tanexc.schoolmenu.presentation.screen.dish.DishViewModel
import ru.tanexc.schoolmenu.presentation.ui.util.isScrollingUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishList(
    modifier: Modifier = Modifier,
    viewModel: DishViewModel
) {
    val scrollState = rememberLazyListState()

    Box(modifier.fillMaxSize()) {
        TopAppBar(
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
                        )
                    )
                } else {
                    Text(
                        stringResource(R.string.dishlist),
                        Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
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
        AnimatedVisibility(
            visible = scrollState.isScrollingUp() && remember { derivedStateOf { scrollState.firstVisibleItemIndex } }.value != 0,
            modifier = Modifier.align(Alignment.BottomEnd),
            enter = slideInVertically { it },
            exit = slideOutVertically { it }
        ) {
            FloatingActionButton(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        scrollState.animateScrollToItem(0)
                    }
                },
                containerColor = colorScheme.secondaryContainer,
                contentColor = contentColorFor(backgroundColor = colorScheme.secondaryContainer),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .height(56.dp)
            ) {
                Icon(
                    Icons.Outlined.ArrowUpward,
                    contentDescription = null,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
        LazyColumn(
            Modifier.fillMaxSize(),
            state = scrollState
        ) {
            items(viewModel.data) { dish ->
                DishCard(dish, onClick = { viewModel.selectDish(dish) })
            }
            item {
                LaunchedEffect(true) {
                    viewModel.nextPage()
                }
            }
        }
    }
}