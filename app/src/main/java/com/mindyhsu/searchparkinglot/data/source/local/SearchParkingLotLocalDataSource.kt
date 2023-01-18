package com.mindyhsu.searchparkinglot.data.source.local

import com.google.gson.Gson
import com.mindyhsu.searchparkinglot.AppApplication
import com.mindyhsu.searchparkinglot.data.*
import com.mindyhsu.searchparkinglot.data.source.SearchParkingLotDataSource
import com.mindyhsu.searchparkinglot.parkinglot.*
import java.io.InputStream
import timber.log.Timber

private const val FILE_ALL = "TCMSV_alldesc.json"
private const val FILE_AVAILABLE = "TCMSV_allavailable.json"

object SearchParkingLotLocalDataSource : SearchParkingLotDataSource {
    override suspend fun login(loginRequestBody: LoginRequestBody): Result<LoginResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllParkingLotList(): Result<AllParkingLot> {
        return try {
            /** Load JSON file */
            val inputStream: InputStream = AppApplication.instance.assets.open(FILE_ALL)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            val result = Gson().fromJson(String(buffer), AllParkingLot::class.java)

            Timber.d("getAllParkingLotList result = $result")
            Result.Success(result)
        } catch (e: Exception) {
            Timber.e("getParkingLotList Exception = ${e.message}")
            Result.Error(e)
        }
    }

    override suspend fun getAvailableParkingLotList(): Result<AvailableParkingLot> {
        return try {
            /** Load JSON file */
            val inputStream: InputStream = AppApplication.instance.assets.open(FILE_AVAILABLE)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            val result = Gson().fromJson(String(buffer), AvailableParkingLot::class.java)

            Timber.d("getAvailableParkingLotList result = $result")
            Result.Success(result)
        } catch (e: Exception) {
            Timber.e("getParkingLotList Exception = ${e.message}")
            Result.Error(e)
        }
    }

    override suspend fun updateUser(
        sessionToken: String,
        objectId: String,
        userUpdateRequestBody: UserUpdateRequestBody
    ): Result<UpdateResponse> {
        TODO("Not yet implemented")
    }
}
