package ru.tanexc.schoolmenu.presentation.screen

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.tanexc.schoolmenu.R
import ru.tanexc.schoolmenu.presentation.screen.dish.DishScreen
import ru.tanexc.schoolmenu.presentation.screen.menu.MenuScreen
import ru.tanexc.schoolmenu.presentation.util.Screen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val selected: MutableState<Screen> = remember { mutableStateOf(Screen.DishList) }
    Scaffold(
        Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selected.value == Screen.Menu,
                    onClick = {
                        selected.value = Screen.Menu
                        navController.navigate(Screen.Menu)
                    },
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Outlined.Article,
                            null
                        )
                    },
                    label = { Text(stringResource(R.string.menu)) },
                    alwaysShowLabel = false
                )


                NavigationBarItem(
                    selected = selected.value == Screen.DishList,
                    onClick = {
                        selected.value = Screen.DishList
                        navController.navigate(Screen.DishList)
                    },
                    icon = {
                        Icon(
                            Icons.Outlined.Restaurant,
                            null
                        )
                    },
                    label = { Text(stringResource(R.string.dish)) },
                    alwaysShowLabel = false
                )


                NavigationBarItem(
                    selected = selected.value == Screen.Objective,
                    onClick = {
                        selected.value = Screen.Objective
                        navController.navigate(Screen.Objective)
                    },
                    icon = {
                        Icon(
                            Icons.Outlined.CameraAlt,
                            null
                        )
                    },
                    label = { Text(stringResource(R.string.objective)) },
                    alwaysShowLabel = false
                )
            }
        }) { padding ->
        Surface {
            NavHost(
                navController,
                Screen.DishList,
                enterTransition = { fadeIn(animationSpec = tween(200)) },
                exitTransition = { fadeOut(animationSpec = tween(200)) }
            ) {
                composable<Screen.DishList> {
                    DishScreen(modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 0.dp, 0.dp, padding.calculateBottomPadding()))
                }

                composable<Screen.Menu> {
                    MenuScreen(modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 0.dp, 0.dp, padding.calculateBottomPadding()), topPadding = padding.calculateTopPadding())
                }

                composable<Screen.Objective> {

                }
            }
        }
    }
}