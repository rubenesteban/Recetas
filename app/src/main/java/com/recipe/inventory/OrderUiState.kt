package com.recipe.inventory


data class OrderUiState(
    val lati: Double = 0.0,
    val long: Double = 0.0,
    val name: String = "",
    val tel: String = "",
    val profession: String = "",
    val userID: String = "",
    val age: Long = 0,
    val save: Boolean = true
)