package com.mindyhsu.searchparkinglot.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mindyhsu.searchparkinglot.data.source.SearchParkingLotRepository
import com.mindyhsu.searchparkinglot.login.LoginViewModel

class ViewModelFactory constructor(
    private val repository: SearchParkingLotRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
