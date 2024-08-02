package ru.tanexc.schoolmenu.domain.model

import androidx.compose.ui.graphics.ImageBitmap

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
)