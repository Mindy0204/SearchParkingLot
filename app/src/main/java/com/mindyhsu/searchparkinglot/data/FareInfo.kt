package com.mindyhsu.searchparkinglot.data

data class FareInfo(
    val WorkingDay: List<DayFare>?,
    val Holiday: List<DayFare>?
)
