package com.mindyhsu.searchparkinglot.data.source.remote

import com.mindyhsu.searchparkinglot.R
import com.mindyhsu.searchparkinglot.data.LoginRequestBody
import com.mindyhsu.searchparkinglot.data.Result
import com.mindyhsu.searchparkinglot.data.source.SearchParkingLotDataSource
import com.mindyhsu.searchparkinglot.network.SearchParkingLotApi
import com.mindyhsu.searchparkinglot.util.Util.isInternetConnected
import com.mindyhsu.searchparkinglot.util.Util.getString
import timber.log.Timber

object SearchParkingLotRemoteDataSource : SearchParkingLotDataSource {
    override suspend fun login(
        applicationId: String,
        loginRequestBody: LoginRequestBody
    ): Result<Boolean> {
        if (!isInternetConnected()) {
            return Result.Fail(getString(R.string.internet_not_connected))
        }

        return try {
            SearchParkingLotApi.retrofitService.login(applicationId, loginRequestBody)
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }

    }

}