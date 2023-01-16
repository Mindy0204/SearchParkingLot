package com.mindyhsu.searchparkinglot.parkinglot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mindyhsu.searchparkinglot.AppApplication
import com.mindyhsu.searchparkinglot.data.ParkingLotDisplayInfo
import java.io.InputStream
import org.json.JSONObject
import timber.log.Timber

class ParkingLotViewModel : ViewModel() {
    private val allParkingLotInfo = loadJSONFromAssets(FILE_ALL)?.let { JSONObject(it) }
    private val availableParkingLotInfo = loadJSONFromAssets(FILE_AVAILABLE)?.let { JSONObject(it) }

    private val _parkingLotInfo = MutableLiveData<List<ParkingLotDisplayInfo>>()
    val parkingLotInfo: LiveData<List<ParkingLotDisplayInfo>>
        get() = _parkingLotInfo

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    var isFinishApp = false

    init {
        _isLoading.value = true
        getParkingLotInfo()
    }

    private fun loadJSONFromAssets(file: String): String? {
        var jsonString: String? = null
        try {
            val inputStream: InputStream = AppApplication.instance.assets.open(file)
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

    /** Combine required display data */
    fun getParkingLotInfo() {
        try {
            val data = allParkingLotInfo?.getJSONObject(JSON_DATA)
            val parkList = data?.getJSONArray(JSON_PARK)
            val dataList = mutableListOf<ParkingLotDisplayInfo>()

            parkList?.let {
                for (i in 0 until it.length()) {
                    val parkItem = it.getJSONObject(i)
                    val availableData = getAvailableParkingLot(parkItem.getString(JSON_ID))

                    val data = ParkingLotDisplayInfo(
                        id = parkItem.getString(JSON_ID),
                        name = parkItem.getString(JSON_NAME),
                        address = parkItem.getString(JSON_ADDRESS),
                        totalCar = parkItem.getInt(JSON_TOTAL_CAR),
                        availableCar = availableData[JSON_AVAILABLE_CAR] ?: DEFAULT_NUM,
                        statusCharging = availableData[JSON_STATUS_CHARGING] ?: DEFAULT_NUM,
                        statusStandby = availableData[JSON_STATUS_STANDBY] ?: DEFAULT_NUM
                    )
                    Timber.d("parkItem = $data")
                    dataList.add(data)
                }
            }
            _parkingLotInfo.value = dataList
            _isLoading.value = false
        } catch (e: Exception) {
            Timber.e("getParkingLotInfo Exception = ${e.message}")
        }
    }

    /** Find the same parking lot with id from the available parking lot list */
    private fun getAvailableParkingLot(parkItemId: String): Map<String, Int> {
        val availableMap = mutableMapOf<String, Int>()
        try {
            val data = availableParkingLotInfo?.getJSONObject(JSON_DATA)
            val parkList = data?.getJSONArray(JSON_PARK)

            parkList?.let {
                for (i in 0 until it.length()) {
                    val availableParkItem = it.getJSONObject(i)

                    // Available car
                    if (availableParkItem.getString(JSON_ID) == parkItemId) {
                        availableMap[JSON_AVAILABLE_CAR] =
                            availableParkItem.getInt(JSON_AVAILABLE_CAR)

                        val statusMap = calculateChargingAndStandby(
                            availableParkItem.getJSONObject(
                                JSON_CHARGE_STATION
                            )
                        )

                        availableMap[JSON_STATUS_CHARGING] = statusMap[JSON_STATUS_CHARGING] ?: DEFAULT_NUM
                        availableMap[JSON_STATUS_STANDBY] = statusMap[JSON_STATUS_STANDBY] ?: DEFAULT_NUM
                    }
                }
            }
        } catch (e: Exception) {
            Timber.e("getParkingLotInfo Exception = ${e.message}")
        }
        return availableMap
    }

    /** Calculate number of charge stations which are on charging status/ standby status **/
    private fun calculateChargingAndStandby(chargeStationItem: JSONObject): Map<String, Int> {
        val scoketStatusList = chargeStationItem.getJSONArray(JSON_SCOKET_STATUS_LIST)
        var chargingNum = 0
        var standbyNum = 0

        for (i in 0 until scoketStatusList.length()) {
            val scoketStatusItem = scoketStatusList.getJSONObject(i)

            if (scoketStatusItem.getString(JSON_STATUS) == JSON_STATUS_CHARGING) {
                chargingNum += 1
            } else if (scoketStatusItem.getString(JSON_STATUS) == JSON_STATUS_STANDBY) {
                standbyNum += 1
            }
        }

        return mapOf(
            Pair(JSON_STATUS_CHARGING, chargingNum),
            Pair(JSON_STATUS_STANDBY, standbyNum)
        )
    }
}
