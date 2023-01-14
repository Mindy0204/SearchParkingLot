package com.mindyhsu.searchparkinglot.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParkingLotDisplayInfo(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val totalCar: Int = -1,
    val availableCar: Int = -1,
    val statusCharging: Int = -1,
    val statusStandby: Int = -1
): Parcelable