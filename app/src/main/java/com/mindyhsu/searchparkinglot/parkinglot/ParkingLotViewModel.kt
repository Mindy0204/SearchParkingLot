package com.mindyhsu.searchparkinglot.parkinglot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mindyhsu.searchparkinglot.AppApplication
import com.mindyhsu.searchparkinglot.data.ParkingLotDisplayInfo
import org.json.JSONObject
import timber.log.Timber
import java.io.InputStream


class ParkingLotViewModel : ViewModel() {
    private val _parkingLotInfo = MutableLiveData<List<ParkingLotDisplayInfo>>()
    val parkingLotInfo: LiveData<List<ParkingLotDisplayInfo>>
        get() = _parkingLotInfo

    init {
        getParkingLotInfo()
    }

    private fun getParkingLotInfo() {
        try {
            val jsonObject = loadJSONFromAssets()?.let { JSONObject(it) }
            val data = jsonObject?.getJSONObject(JSON_DATA)
            val parkList = data?.getJSONArray(JSON_PARK)
            val dataList = mutableListOf<ParkingLotDisplayInfo>()

            parkList?.let {
                for (i in 0 until it.length()) {
                    val parkItem = it.getJSONObject(i)
                    val data = ParkingLotDisplayInfo(
                        id = parkItem.getString(JSON_ID),
                        name = parkItem.getString(JSON_NAME),
                        address = parkItem.getString(JSON_ADDRESS),
                        totalCar = parkItem.getInt(JSON_TOTAL_CAR)
                    )
                    Timber.d("parkItem = $data")
                    dataList.add(data)
                }
            }
            _parkingLotInfo.value = dataList

        } catch (e: Exception) {
            Timber.e("getParkingLotInfo Exception = ${e.message}")
        }
    }

    private fun loadJSONFromAssets(): String? {
        var jsonString: String? = null
        try {
            val inputStream: InputStream = AppApplication.instance.assets.open(FILE_ALL)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            jsonString = String(buffer)
        } catch (e: Exception) {
            Timber.e("loadJSONFromAssets Exception = ${e.message}")
        }
        return jsonString
    }

    fun getAvailableParkingLot() {}
}