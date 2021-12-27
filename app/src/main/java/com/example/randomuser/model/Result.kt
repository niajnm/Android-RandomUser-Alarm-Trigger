package com.example.randomuser.model

data class Result(
    val cell: String,
    val email: String,
    val gender: String,
    val location: Location,
    var name: Name,
    val nat: String,
    val phone: String,
    val picture: Picture

)