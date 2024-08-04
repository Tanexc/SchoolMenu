package ru.tanexc.schoolmenu.presentation.screen.dish.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.presentation.ui.theme.Typography

@Composable
fun DishCard(
    dish: Dish,
    onClick: (Dish) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(216.dp)
            .clickable(remember { MutableInteractionSource() }, null) {
                onClick(dish)
            }
            .background(
                MaterialTheme.colorScheme.primaryContainer.copy(0.5f),
                RoundedCornerShape(16.dp)
            )

    ) {
        Image(
            dish.image,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.4f)
                .clip(RoundedCornerShape(16.dp))
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp, 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                dish.title,
                style = Typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .basicMarquee()
            )
            Column {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Белки", fontWeight = FontWeight.Bold)
                    Text("${dish.protein} гр")
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Жиры", fontWeight = FontWeight.Bold)
                    Text("${dish.fats} гр")
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Углеводы", fontWeight = FontWeight.Bold)
                    Text("${dish.carbon} гр")
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Калории", fontWeight = FontWeight.Bold)
                    Text("${dish.calories} ккал")
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Вредность", fontWeight = FontWeight.Bold)
                    Text(dish.harm.harm, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}