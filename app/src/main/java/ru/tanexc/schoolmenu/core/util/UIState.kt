package ru.tanexc.schoolmenu.core.util

sealed class UIState {
    data object Loading: UIState()

    data object Success : UIState()

    data class Error(val message: String): UIState()
}