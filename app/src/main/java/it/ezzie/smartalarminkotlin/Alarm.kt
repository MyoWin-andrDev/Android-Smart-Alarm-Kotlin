package it.ezzie.smartalarminkotlin

data class Alarm(
    var id : Int,
    var Hour : Int,
    var Minute : Int,
    var Day : List<String>,
    var Unit : String,
    var Label : String? = null,
    var On : Boolean? = null
)
