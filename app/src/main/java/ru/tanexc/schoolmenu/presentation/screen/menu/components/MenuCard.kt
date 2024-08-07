package ru.tanexc.schoolmenu.presentation.screen.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.tanexc.schoolmenu.R
import ru.tanexc.schoolmenu.domain.model.Menu

@Composable
fun MenuCard(
    menu: Menu,
    onMealClick: () -> Unit,
    additional: @Composable ColumnScope.() -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.secondaryContainer.copy(0.5f),
                RoundedCornerShape(16.dp)
            )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            menu.meals.sortedBy { it.type.ordinal }.forEachIndexed { index, meal ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable { onMealClick() },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        meal.type.title + ": " + meal.dishes.joinToString(", "),
                        modifier = Modifier.fillMaxWidth(0.8f),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "${meal.cost} " + stringResource(R.string.rub),
                    )
                }
                if (index != meal.dishes.lastIndex) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.outline.copy(0.4f))
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outline)
        )
        additional()
    }
}