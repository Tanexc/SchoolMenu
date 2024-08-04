package ru.tanexc.schoolmenu.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen() {

    Scaffold(Modifier.fillMaxSize(), bottomBar = {
        NavigationBar {
            NavigationBarItem(selected = , onClick = { /*TODO*/ }, icon = { /*TODO*/ })
            NavigationBarItem(selected = , onClick = { /*TODO*/ }, icon = { /*TODO*/ })
            NavigationBarItem(selected = , onClick = { /*TODO*/ }, icon = { /*TODO*/ })
        }
    }) {

    }
}