package com.mindyhsu.searchparkinglot.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindyhsu.searchparkinglot.data.LoginRequestBody
import com.mindyhsu.searchparkinglot.network.SearchParkingLotApi
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    init {
        login()
    }

    fun login() {
        viewModelScope.launch {
            SearchParkingLotApi.retrofitService.login(
                applicationId = "vqYuKPOkLQLYHhk4QTGsGKFwATT4mBIGREI2m8eD",
                requestBody = LoginRequestBody(username = "***REMOVED***", password = "***REMOVED***")
            )
        }
    }
}