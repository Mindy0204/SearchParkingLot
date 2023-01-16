package com.mindyhsu.searchparkinglot.data.source.remote

import com.mindyhsu.searchparkinglot.R
import com.mindyhsu.searchparkinglot.data.*
import com.mindyhsu.searchparkinglot.data.source.SearchParkingLotDataSource
import com.mindyhsu.searchparkinglot.network.SearchParkingLotApi
import com.mindyhsu.searchparkinglot.util.Util.isInternetConnected
import com.mindyhsu.searchparkinglot.util.Util.getString
import timber.log.Timber

private const val HEADER_VALUE_APPLICATION_ID = "vqYuKPOkLQLYHhk4QTGsGKFwATT4mBIGREI2m8eD"

object SearchParkingLotRemoteDataSource : SearchParkingLotDataSource {
    override suspend fun login(
        loginRequestBody: LoginRequestBody
    ): Result<LoginResponse> {
        if (!isInternetConnected()) {
            return Result.Fail(getString(R.string.internet_not_connected))
        }

        return try {
            val result = SearchParkingLotApi.retrofitService.login(HEADER_VALUE_APPLICATION_ID, loginRequestBody)
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }

    }

    override suspend fun updateUser(
        sessionToken: String,
        objectId: String,
        userUpdateRequestBody: UserUpdateRequestBody
    ): Result<UpdateResponse> {
        if (!isInternetConnected()) {
            return Result.Fail(getString(R.string.internet_not_connected))
        }

        return try {
            val result = SearchParkingLotApi.retrofitService.updateUser(
                HEADER_VALUE_APPLICATION_ID,
                sessionToken,
                objectId,
                userUpdateRequestBody
            )
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}