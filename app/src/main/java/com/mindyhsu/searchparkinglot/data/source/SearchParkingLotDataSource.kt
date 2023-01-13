package com.mindyhsu.searchparkinglot.data.source

import com.mindyhsu.searchparkinglot.data.LoginRequestBody
import com.mindyhsu.searchparkinglot.data.Result

interface SearchParkingLotDataSource {
    suspend fun login(applicationId: String, loginRequestBody: LoginRequestBody): Result<Boolean>
}