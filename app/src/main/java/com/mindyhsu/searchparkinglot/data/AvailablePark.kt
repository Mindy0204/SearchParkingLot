package com.mindyhsu.searchparkinglot.data

data class AvailablePark(
    val id: String,
    val availablecar: Int,
    val availablemotor: Int,
    val availablebus: Int,
    val ChargeStation: ChargeStation?
)
