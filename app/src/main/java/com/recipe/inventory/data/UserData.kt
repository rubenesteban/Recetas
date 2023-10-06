package com.recipe.inventory.data



data class UserData(
    val id: Int = 0,
    var userID: String = "",
    var name: String = "",
    var profession: String = "",
    var age: String = "",
    var qua:String = "",
    var url:String = ""

)



data class UserImage(

    var userID: String = "",
    var url:String = ""
)