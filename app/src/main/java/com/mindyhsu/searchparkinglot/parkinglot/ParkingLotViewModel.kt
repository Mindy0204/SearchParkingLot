package com.mindyhsu.searchparkinglot.parkinglot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindyhsu.searchparkinglot.R
import com.mindyhsu.searchparkinglot.data.*
import com.mindyhsu.searchparkinglot.data.source.SearchParkingLotRepository
import com.mindyhsu.searchparkinglot.network.LoadApiStatus
import com.mindyhsu.searchparkinglot.util.Util
import kotlinx.coroutines.launch
import timber.log.Timber

class ParkingLotViewModel(private val repository: SearchParkingLotRepository) : ViewModel() {
    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private val _allParkingLotList = MutableLiveData<AllParkingLot>()
    val allParkingLotList: LiveData<AllParkingLot>
        get() = _allParkingLotList

    private val _availableParkingLotList = MutableLiveData<AvailableParkingLot>()
    val availableParkingLotList: LiveData<AvailableParkingLot>
        get() = _availableParkingLotList

    private val _parkingLotDisplayInfo = MutableLiveData<List<ParkingLotDisplayInfo>>()
    val parkingLotDisplayInfo: LiveData<List<ParkingLotDisplayInfo>>
        get() = _parkingLotDisplayInfo

    var isFinishApp = false

    init {
        getAllParkingLotList()
        getAvailableParkingLotList()
    }

    fun getAllParkingLotList() {
        viewModelScope.launch {
            _status.value = LoadApiStatus.LOADING

            val result = repository.getAllParkingLotList()

            _allParkingLotList.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value =
                        Util.getString(R.string.operation_failed)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
        }
    }

    fun getAvailableParkingLotList() {
        viewModelScope.launch {
            _status.value = LoadApiStatus.LOADING

            val result = repository.getAvailableParkingLotList()

            _availableParkingLotList.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value =
                        Util.getString(R.string.operation_failed)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
        }
    }

    /** Combine required display data */
    fun combineDisplayData() {
        _allParkingLotList.value?.data?.let { allData ->
            val dataList = mutableListOf<ParkingLotDisplayInfo>()
            for (parkItem in allData.park) {
                val availableData = getAvailableParkingLotData(parkItem.id)

                val data = ParkingLotDisplayInfo(
                    id = parkItem.id,
                    name = parkItem.name,
                    address = parkItem.address,
                    totalCar = parkItem.totalcar,
                    availableCar = availableData[JSON_AVAILABLE_CAR] ?: DEFAULT_NUM,
                    statusCharging = availableData[JSON_STATUS_CHARGING] ?: DEFAULT_NUM,
                    statusStandby = availableData[JSON_STATUS_STANDBY] ?: DEFAULT_NUM
                )
                Timber.d("displayData = $data")
                dataList.add(data)
            }
            _parkingLotDisplayInfo.value = dataList
        }
    }

    /** Find the same parking lot with id from the available parking lot list */
    private fun getAvailableParkingLotData(parkItemId: String): Map<String, Int> {
        val availableMap = mutableMapOf<String, Int>()
        _availableParkingLotList.value?.data?.let { availableData ->
            for (parkItem in availableData.park) {
                if (parkItem.id == parkItemId) {
                    availableMap[JSON_AVAILABLE_CAR] =
                        parkItem.availablecar

                    parkItem.ChargeStation?.let { chargeStation ->
                        val statusMap = calculateChargingAndStandby(
                            chargeStation.scoketStatusList
                        )

                        availableMap[JSON_STATUS_CHARGING] =
                            statusMap[JSON_STATUS_CHARGING] ?: DEFAULT_NUM
                        availableMap[JSON_STATUS_STANDBY] =
                            statusMap[JSON_STATUS_STANDBY] ?: DEFAULT_NUM
                    }
                }
            }
        }
        return availableMap
    }

    /** Calculate number of charge stations which are on charging status/ standby status **/
    private fun calculateChargingAndStandby(scoketStatusList: List<ScoketStatusList>): Map<String, Int> {
        var chargingNum = 0
        var standbyNum = 0

        for (scoketStatusItem in scoketStatusList) {
            if (scoketStatusItem.spot_status == JSON_STATUS_CHARGING) {
                chargingNum += 1
            } else if (scoketStatusItem.spot_status == JSON_STATUS_STANDBY) {
                standbyNum += 1
            }
        }

        return mapOf(
            Pair(JSON_STATUS_CHARGING, chargingNum),
            Pair(JSON_STATUS_STANDBY, standbyNum)
        )
    }
}
