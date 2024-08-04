package ru.tanexc.schoolmenu.core.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.tanexc.schoolmenu.presentation.screen.dish.DishViewModel

val viewModelModule = module {
    singleOf(::DishViewModel)
}