package com.mindyhsu.searchparkinglot

import android.app.Application
import com.mindyhsu.searchparkinglot.data.source.SearchParkingLotRepository
import com.mindyhsu.searchparkinglot.util.ServiceLocator
import kotlin.properties.Delegates
import timber.log.Timber

class AppApplication : Application() {
    val repository: SearchParkingLotRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var instance: AppApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.TIMBER_VISIABLE) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
