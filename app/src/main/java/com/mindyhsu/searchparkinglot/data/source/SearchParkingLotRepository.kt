package com.mindyhsu.searchparkinglot.data.source

import com.mindyhsu.searchparkinglot.data.*

interface SearchParkingLotRepository {
    suspend fun login(loginRequestBody: LoginRequestBody): Result<LoginResponse>

    suspend fun updateUser(
        sessionToken: String,
        objectId: String,
        userUpdateRequestBody: UserUpdateRequestBody
    ): Result<UpdateResponse>
}
