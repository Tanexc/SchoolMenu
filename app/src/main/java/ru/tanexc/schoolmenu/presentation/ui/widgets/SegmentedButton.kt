package ru.tanexc.schoolmenu.presentation.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentedButton(
    selected: Boolean,
    modifier: Modifier,
    onSelect: () -> Unit,
    shape: RoundedCornerShape,
    borderWidth: Dp,
    colors: SegmentedButtonColors = SegmentedButtonDefaults.colors(),
    content: @Composable () -> Unit
) {
    if (selected) {
        Box(
            modifier = modifier
                .height(32.dp)
                .background(colors.activeContainerColor, shape)
                .border(borderWidth, colors.activeBorderColor, shape)
                .clip(shape)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(Icons.Default.Check, null, tint = colors.activeContentColor)
                Spacer(modifier = Modifier.size(4.dp))
                content()
            }
        }
    } else {
        Box(
            modifier = modifier
                .height(32.dp)
                .clip(shape)
                .background(colors.disabledInactiveContainerColor, shape)
                .border(borderWidth, colors.disabledInactiveBorderColor, shape)
                .clickable { onSelect() },
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                content()
            }
        }
    }
}