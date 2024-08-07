package ru.tanexc.schoolmenu.presentation.screen.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.tanexc.schoolmenu.domain.model.DayOfWeek
import ru.tanexc.schoolmenu.presentation.ui.theme.Typography

@Composable
fun RowScope.DayOfWeekCard(
    selected: DayOfWeek,
    onClick: () -> Unit,
    day: DayOfWeek,
    text: String
) {
    Box(
        Modifier
            .fillMaxSize()
            .weight(1f)
            .padding(4.dp)
            .background(
                MaterialTheme.colorScheme.primary.copy(if (isSystemInDarkTheme()) if (selected == day) 1f else 0.6f else if (selected == day) 0.8f else 0.6f),
                RoundedCornerShape(8.dp)
            )
            .then(
                if (selected == day) Modifier.border(
                    2.dp,
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(8.dp)
                )
                else Modifier
            )
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() },
    ) {
        Text(
            day.text,
            Modifier.align(Alignment.Center),
            fontStyle = Typography.titleLarge.copy(fontWeight = FontWeight.Bold).fontStyle,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text,
            Modifier
                .align(Alignment.BottomCenter)
                .padding(0.dp, 0.dp, 0.dp, 8.dp),
            fontStyle = Typography.bodyMedium.fontStyle,
            color = MaterialTheme.colorScheme.onPrimary.copy(0.5f)
        )
    }
}