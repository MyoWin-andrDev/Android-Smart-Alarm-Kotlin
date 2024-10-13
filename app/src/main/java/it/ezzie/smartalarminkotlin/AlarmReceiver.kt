package it.ezzie.smartalarminkotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var alarmLabel : String
        if(intent != null){
            alarmLabel = intent.getStringExtra("alarmLabel").toString()
        }
        else{
            alarmLabel = "Alarm is ringing"
        }
        if (context != null) {
            createChannel(context,alarmLabel)
        }
    }
    private fun createChannel(context : Context , alarmLabel : String){

    }
}