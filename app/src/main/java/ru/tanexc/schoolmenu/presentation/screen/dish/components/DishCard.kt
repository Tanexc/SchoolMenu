package ru.tanexc.schoolmenu.presentation.screen.dish.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.presentation.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DishCard(
    dish: Dish,
    onClick: (Dish) -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(360.dp)
            .clickable(remember{MutableInteractionSource()}, null) {
                onClick(dish)
            }
    ) {
        AsyncImage(
            model = dish.image,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .matchParentSize()
                .fillMaxWidth(0.4f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                dish.title,
                style = Typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .basicMarquee()
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Белки", fontWeight = FontWeight.Bold)
                Text("${dish.protein} гр")
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Жиры", fontWeight = FontWeight.Bold)
                Text("${dish.fats} гр")
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Углеводы", fontWeight = FontWeight.Bold)
                Text("${dish.carbon} гр")
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Калории", fontWeight = FontWeight.Bold)
                Text("${dish.calories} ккал")
            }

            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Вредность", fontWeight = FontWeight.Bold)
                Text(dish.harm.harm, fontWeight = FontWeight.Bold)
            }
        }
    }
}