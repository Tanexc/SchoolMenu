package ru.tanexc.schoolmenu.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.tanexc.schoolmenu.core.di.databaseModule
import ru.tanexc.schoolmenu.core.di.viewModelModule

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(databaseModule, viewModelModule)
        }
    }
}