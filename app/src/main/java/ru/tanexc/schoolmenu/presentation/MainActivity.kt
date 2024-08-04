package ru.tanexc.schoolmenu.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import ru.tanexc.schoolmenu.R
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.domain.model.Harm
import ru.tanexc.schoolmenu.presentation.screen.MainScreen
import ru.tanexc.schoolmenu.presentation.screen.dish.components.EditDishScreen
import ru.tanexc.schoolmenu.presentation.ui.theme.SchoolMenuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SchoolMenuTheme {
                MainScreen()
            }
        }
    }
}