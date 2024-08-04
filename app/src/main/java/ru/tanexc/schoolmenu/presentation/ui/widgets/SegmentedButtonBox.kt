package ru.tanexc.schoolmenu.presentation.ui.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SegmentedButtonBox(
    modifier: Modifier,
    rowCount: Int,
    columnCount: Int,
    colors: SegmentedButtonColors = SegmentedButtonDefaults.colors(),
    borderWidth: Dp = 1.5.dp,
    data: List<T>,
    initial: T? = null,
    onSelect: (T) -> Unit,
    itemContent: @Composable (T) -> Unit,
) {
    val selectedItem: MutableState<T?> = remember { mutableStateOf(initial) }

    LaunchedEffect(selectedItem.value) {
        if (selectedItem.value != null) {
            onSelect(selectedItem.value!!)
        }
    }

    Column(
        modifier
            .clip(RoundedCornerShape(16.dp))
            .border(borderWidth, colors.disabledInactiveBorderColor, RoundedCornerShape(16.dp))
    ) {
        repeat(rowCount) { r ->
            Row(Modifier.fillMaxWidth()) {
                repeat(columnCount) { c ->
                    if (r * columnCount + c < data.size) {
                        val shape = if (r == 0 && c == 0) {
                            topStartShape(
                                rowCount,
                                columnCount
                            )
                        } else if (r == 0 && c == columnCount - 1) {
                            topEndShape(
                                rowCount,
                                columnCount
                            )
                        } else if (r == rowCount - 1 && c == 0) {
                            bottomStartShape(
                                rowCount,
                                if (r * columnCount + c + 1 == data.size) 1 else columnCount
                            )
                        } else if (r == rowCount - 1 && c == columnCount - 1) {
                            bottomEndShape(
                                rowCount,
                                columnCount
                            )
                        } else middleShape()
                        SegmentedButton(
                            selected = selectedItem.value == data[r * columnCount + c],
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            onSelect = { selectedItem.value = data[r * columnCount + c] },
                            borderWidth = borderWidth / 2,
                            shape = shape
                        ) {
                            itemContent(data[r * columnCount + c])
                        }
                    }
                }
            }
        }
    }
}
