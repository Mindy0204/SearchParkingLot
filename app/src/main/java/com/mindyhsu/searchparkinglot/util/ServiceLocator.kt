package com.mindyhsu.searchparkinglot.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.mindyhsu.searchparkinglot.data.source.DefaultSearchParkingLotRepository
import com.mindyhsu.searchparkinglot.data.source.SearchParkingLotRepository
import com.mindyhsu.searchparkinglot.data.source.remote.SearchParkingLotRemoteDataSource

object ServiceLocator {

    @Volatile
    var searchParkingLotRepository: SearchParkingLotRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): SearchParkingLotRepository {
        synchronized(this) {
            return searchParkingLotRepository ?: createSearchParkingLotRepository(context)
        }
    }

    private fun createSearchParkingLotRepository(context: Context): SearchParkingLotRepository {
        return DefaultSearchParkingLotRepository(SearchParkingLotRemoteDataSource)
    }
}
