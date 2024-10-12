package it.ezzie.smartalarminkotlin

data class Alarm(
    var id: Int?,
    var Hour: String,
    var Minute: String,
    var Day: String? = null,
    var Unit: String,
    var Label: String? = null,
    var On: Boolean
)
