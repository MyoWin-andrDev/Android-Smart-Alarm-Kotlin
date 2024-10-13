package it.ezzie.smartalarminkotlin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmLabel : String
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
        val channelId = "alarm_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ringtone : Uri = Uri.parse("android.resource://${context.packageName}/${R.raw.playing_god}")
            //Creating Notification Channel
            val notificationChannel: NotificationChannel = NotificationChannel(channelId, "Alarm Notification", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.setSound(ringtone,null)
            //Creating Notification Manager
            val notificationManager : NotificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
            //Creating Notification Builder
            val builder : NotificationCompat.Builder = NotificationCompat.Builder(context, channelId)
                .setContentTitle("Alarm")
                .setContentText(alarmLabel)
                .setSmallIcon(R.drawable.alarm_icon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
            notificationManager.notify(1,builder.build())
        }

    }
}