package com.mindyhsu.searchparkinglot.data.source

import com.mindyhsu.searchparkinglot.data.LoginRequestBody
import com.mindyhsu.searchparkinglot.data.Result

interface SearchParkingLotRepository {
    suspend fun login(applicationId: String, loginRequestBody: LoginRequestBody): Result<Boolean>
}