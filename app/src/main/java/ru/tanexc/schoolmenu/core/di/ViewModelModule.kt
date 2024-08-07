package ru.tanexc.schoolmenu.core.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.tanexc.schoolmenu.presentation.screen.dish.DishViewModel
import ru.tanexc.schoolmenu.presentation.screen.meal.CreateMealViewModel
import ru.tanexc.schoolmenu.presentation.screen.menu.viewModel.MenuCreateViewModel
import ru.tanexc.schoolmenu.presentation.screen.menu.viewModel.MenuViewModel

val viewModelModule = module {
    singleOf(::DishViewModel)
    singleOf(::MenuViewModel)
    singleOf(::CreateMealViewModel)
    singleOf(::MenuCreateViewModel)
}