package com.mindyhsu.searchparkinglot.data

data class LoginResponse(
    val objectId: String,
    val name: String,
    val timezone: String,
    val phone: String,
    val createdAt: String,
    val updatedAt: String,
    val sessionToken: String
)