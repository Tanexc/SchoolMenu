package ru.tanexc.schoolmenu.presentation.util

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object Menu: Screen()

    @Serializable
    data object Week: Screen()

    @Serializable
    data object CreateMenu: Screen()

    @Serializable
    data object DishList: Screen()

    @Serializable
    data object DishInfo: Screen()

    @Serializable
    data object Objective: Screen()
}