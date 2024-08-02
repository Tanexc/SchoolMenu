package ru.tanexc.schoolmenu.core.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.tanexc.schoolmenu.data.local.MainDatabase

val database = module {
    single<MainDatabase> {
        Room.databaseBuilder(
            context = androidApplication().applicationContext,
            klass = MainDatabase::class.java,
            name = "maindatabase"
        ).build()
    }
}