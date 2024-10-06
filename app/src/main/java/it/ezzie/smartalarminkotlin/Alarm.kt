package it.ezzie.smartalarminkotlin

data class Alarm(
    var id : Int,
    var Hour : Int,
    var Minute : Int,
    var Unit : String,
    var Label : String,
    var On : Boolean? = null
)
