package com.mindyhsu.searchparkinglot.userupdate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindyhsu.searchparkinglot.R
import com.mindyhsu.searchparkinglot.data.*
import com.mindyhsu.searchparkinglot.data.source.SearchParkingLotRepository
import com.mindyhsu.searchparkinglot.login.UserManager
import com.mindyhsu.searchparkinglot.network.LoadApiStatus
import com.mindyhsu.searchparkinglot.util.Util
import java.util.*
import kotlinx.coroutines.launch
import timber.log.Timber

class UserUpdateViewModel(private val repository: SearchParkingLotRepository) : ViewModel() {
    private val timeZoneIds = TimeZone.getAvailableIDs()

    private val _timeZoneList = MutableLiveData<ArrayList<String>>()
    val timeZoneList: LiveData<ArrayList<String>>
        get() = _timeZoneList

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private val _updateResult = MutableLiveData<UpdateResponse>()
    val updateResult: LiveData<UpdateResponse>
        get() = _updateResult

    private val _gmt = MutableLiveData<String>()
    val gmt: LiveData<String>
        get() = _gmt

    init {
        getSystemSupportedTimeZone()
    }

    private fun getSystemSupportedTimeZone() {
        val dataList = mutableListOf<String>()
        for (i in timeZoneIds.indices) {
            dataList.add(timeZoneIds[i])
        }
        _timeZoneList.value = dataList as ArrayList<String>
    }

    fun selectTimeZone(itemNum: Int) {
        Timber.d("timeZoneIds[itemNum] name = ${timeZoneIds[itemNum]}")
        _gmt.value =
            ((TimeZone.getTimeZone(timeZoneIds[itemNum]).rawOffset) / (1000 * 60 * 60)).toString()
    }

    fun updateUserTimeZone(timeZone: String) {
        viewModelScope.launch {
            _status.value = LoadApiStatus.LOADING

            val result = repository.updateUser(
                sessionToken = UserManager.sessionToken ?: "",
                objectId = UserManager.objectId ?: "",
                userUpdateRequestBody = UserUpdateRequestBody(timezone = timeZone)
            )
            _updateResult.value = when (result) {
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
}
