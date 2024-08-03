package ru.tanexc.schoolmenu.domain.model

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import ru.tanexc.schoolmenu.core.util.toByteArray
import ru.tanexc.schoolmenu.data.local.entity.main.DishEntity

/**
 * Модель блюда
 */
data class Dish(
    val id: Long,
    val title: String,
    val image: ImageBitmap,
    val calories: Float,
    val protein: Float,
    val fats: Float,
    val carbon: Float,
    val weight: Float,
    val harm: Harm = Harm.NotSpecified,
    val cost: Float
): Domain {
    override fun asEntity(): DishEntity = DishEntity(
        id,
        title,
        image.toByteArray(),
        calories,
        protein,
        fats,
        carbon,
        weight,
        harm,
        cost
    )
}