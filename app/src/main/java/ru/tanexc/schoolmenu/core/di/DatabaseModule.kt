package ru.tanexc.schoolmenu.core.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.tanexc.schoolmenu.data.local.MainDatabase
import ru.tanexc.schoolmenu.data.local.dao.DishDao
import ru.tanexc.schoolmenu.data.local.dao.MealDao
import ru.tanexc.schoolmenu.data.local.dao.MealDishDao
import ru.tanexc.schoolmenu.data.local.dao.MenuDao
import ru.tanexc.schoolmenu.data.local.dao.MenuMealDao
import ru.tanexc.schoolmenu.data.repository.DishRepositoryImpl
import ru.tanexc.schoolmenu.data.repository.MealRepositoryImpl
import ru.tanexc.schoolmenu.data.repository.DishRepository
import ru.tanexc.schoolmenu.data.repository.MealRepository
import ru.tanexc.schoolmenu.data.repository.MenuRepository
import ru.tanexc.schoolmenu.data.repository.MenuRepositoryImpl

val database = module {
    single<MainDatabase> {
        Room.databaseBuilder(
            context = androidApplication().applicationContext,
            klass = MainDatabase::class.java,
            name = "maindatabase"
        ).build()
    }

    single<DishDao> { get<MainDatabase>().dishDao }
    single<MealDao> { get<MainDatabase>().mealDao }
    single<MenuDao> { get<MainDatabase>().menuDao }

    single<MealDishDao> { get<MainDatabase>().mealDishDao }
    single<MenuMealDao> { get<MainDatabase>().menuMealDao }

    singleOf(::DishRepositoryImpl) bind DishRepository::class
    singleOf(::MealRepositoryImpl) bind MealRepository::class
    singleOf(::MenuRepositoryImpl) bind MenuRepository::class
}