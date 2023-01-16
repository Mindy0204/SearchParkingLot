package com.mindyhsu.searchparkinglot.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindyhsu.searchparkinglot.R
import com.mindyhsu.searchparkinglot.data.LoginRequestBody
import com.mindyhsu.searchparkinglot.data.LoginResponse
import com.mindyhsu.searchparkinglot.data.Result
import com.mindyhsu.searchparkinglot.data.source.SearchParkingLotRepository
import com.mindyhsu.searchparkinglot.network.LoadApiStatus
import com.mindyhsu.searchparkinglot.util.Util.getString
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: SearchParkingLotRepository) : ViewModel() {

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse>
        get() = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _status.value = LoadApiStatus.LOADING

            val result = repository.login(
                loginRequestBody = LoginRequestBody(username = email, password = password)
            )
            _loginResult.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    setUserData(result.data)
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
                        getString(R.string.operation_failed)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
        }
    }

    private fun setUserData(loginResponse: LoginResponse) {
        UserManager.email = loginResponse.name
        UserManager.objectId = loginResponse.objectId
        UserManager.sessionToken = loginResponse.sessionToken
    }
}
