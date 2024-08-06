package ru.tanexc.schoolmenu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.tanexc.schoolmenu.data.local.dao.DishDao
import ru.tanexc.schoolmenu.data.local.dao.MealDao
import ru.tanexc.schoolmenu.data.local.dao.MealDishDao
import ru.tanexc.schoolmenu.data.local.dao.MenuDao
import ru.tanexc.schoolmenu.data.local.dao.MenuMealDao
import ru.tanexc.schoolmenu.data.local.entity.main.DishEntity
import ru.tanexc.schoolmenu.data.local.entity.main.MealEntity
import ru.tanexc.schoolmenu.data.local.entity.main.MenuEntity
import ru.tanexc.schoolmenu.data.local.entity.supportive.MealDish
import ru.tanexc.schoolmenu.data.local.entity.supportive.MenuForWeek
import ru.tanexc.schoolmenu.data.local.entity.supportive.MenuMeal

@Database(
    entities = [DishEntity::class, MealEntity::class, MenuEntity::class, MenuMeal::class, MealDish::class, MenuForWeek::class],
    exportSchema = false,
    version = 1,
)
abstract class MainDatabase : RoomDatabase() {
    abstract val dishDao: DishDao
    abstract val mealDao: MealDao
    abstract val menuDao: MenuDao

    abstract val mealDishDao: MealDishDao
    abstract val menuMealDao: MenuMealDao
}