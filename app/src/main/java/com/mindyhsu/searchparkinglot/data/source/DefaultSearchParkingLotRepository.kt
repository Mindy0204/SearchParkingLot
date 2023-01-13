package com.mindyhsu.searchparkinglot.data.source

import com.mindyhsu.searchparkinglot.data.LoginRequestBody
import com.mindyhsu.searchparkinglot.data.Result

class DefaultSearchParkingLotRepository(private val remoteDataSource: SearchParkingLotDataSource) :
    SearchParkingLotRepository {
    override suspend fun login(
        applicationId: String,
        loginRequestBody: LoginRequestBody
    ): Result<Boolean> {
        return remoteDataSource.login(applicationId, loginRequestBody)
    }

}