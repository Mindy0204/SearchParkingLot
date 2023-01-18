package com.mindyhsu.searchparkinglot.data.source

import com.mindyhsu.searchparkinglot.data.*

class DefaultSearchParkingLotRepository(
    private val remoteDataSource: SearchParkingLotDataSource,
    private val localDataSource: SearchParkingLotDataSource
) :
    SearchParkingLotRepository {
    override suspend fun login(
        loginRequestBody: LoginRequestBody
    ): Result<LoginResponse> {
        return remoteDataSource.login(loginRequestBody)
    }

    override suspend fun getAllParkingLotList(): Result<AllParkingLot> {
        return localDataSource.getAllParkingLotList()
    }

    override suspend fun getAvailableParkingLotList(): Result<AvailableParkingLot> {
        return localDataSource.getAvailableParkingLotList()
    }

    override suspend fun updateUser(
        sessionToken: String,
        objectId: String,
        userUpdateRequestBody: UserUpdateRequestBody
    ): Result<UpdateResponse> {
        return remoteDataSource.updateUser(sessionToken, objectId, userUpdateRequestBody)
    }
}
