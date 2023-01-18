package com.mindyhsu.searchparkinglot.data.source

import com.mindyhsu.searchparkinglot.data.*

interface SearchParkingLotDataSource {
    suspend fun login(loginRequestBody: LoginRequestBody): Result<LoginResponse>

    suspend fun getAllParkingLotList(): Result<AllParkingLot>

    suspend fun getAvailableParkingLotList(): Result<AvailableParkingLot>

    suspend fun updateUser(
        sessionToken: String,
        objectId: String,
        userUpdateRequestBody: UserUpdateRequestBody
    ): Result<UpdateResponse>
}
