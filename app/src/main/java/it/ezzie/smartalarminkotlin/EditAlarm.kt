package it.ezzie.smartalarminkotlin

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import it.ezzie.smartalarminkotlin.databinding.ActivityEditAlarmBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class EditAlarm : AppCompatActivity() {
    private lateinit var binding: ActivityEditAlarmBinding
    private var calendar: Calendar = Calendar.getInstance()
    private lateinit var databaseHelper: DatabaseHelper
    private var unit: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditAlarmBinding.inflate(layoutInflater)
        databaseHelper = DatabaseHelper(this)
        setContentView(binding.root)
        returnValue()
        initUpdateUI();
        initTimePicker()
        initListener()
    }
    private fun returnValue() : Pair<Int, Alarm?>{
        val id = intent.getIntExtra("alarmId",-1)
        val alarm = if(id != -1){
            databaseHelper.getAlarmById(id)
        }else{ null }
        return Pair(id,alarm)
    }
    private fun initUpdateUI(){
        val (id,alarm) = returnValue()
        if(alarm != null){
            binding.timePicker.hour = alarm.Hour.toInt()
            binding.timePicker.minute = alarm.Minute.toInt()
            binding.alarmEditTxt.setText(alarm.Label)
            binding.btnOK.text = "Update"
            binding.btnCancel.text = "Delete"
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun initTimePicker() {
        //Init TimePicker
        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            val currentTotalMinute =
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60 + Calendar.getInstance()
                    .get(Calendar.MINUTE)
            var totalSelectedMinute = hourOfDay * 60 + minute
            if (totalSelectedMinute < currentTotalMinute) {
                totalSelectedMinute += 1440
            }
            val totalResultMinute = totalSelectedMinute - currentTotalMinute
            var resultHour = totalResultMinute / 60
            var resultMinute = totalResultMinute % 60
            if (totalResultMinute < 0) {
                resultHour = -resultHour
                resultMinute = -resultMinute
            }
            binding.timeCount.text = "Your alarm will ring in $resultHour hr $resultMinute min"
        }
    }
    private fun initListener(){
        val (id,alarm) =returnValue()
        //Btn OK
        binding.btnOK.setOnClickListener(){
            val hour = calendar.get(Calendar.HOUR_OF_DAY).toString()
            val minute = calendar.get(Calendar.MINUTE).toString()
            val label = binding.alarmEditTxt.text.toString()
            unit = SimpleDateFormat("a").format(calendar.time)
            //Create Alarm
            if(alarm == null){
                val alarmCreate = Alarm(id, hour, minute.format("%02d"),null, unit, label, true)
                scheduleAlarm(this,alarmCreate)
                databaseHelper.createData(alarmCreate)
                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
                finish()
            }
            //Update
            else if(alarm != null){
                if(hour != alarm.Hour || minute != alarm.Minute || label != alarm.Label){
                    val alarmUpdate = Alarm(id, hour, minute.format("%02d"),null, unit, label, true)
                    scheduleAlarm(this,alarmUpdate)
                    databaseHelper.updateData(alarmUpdate)
                    Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        //Btn Cancel
        binding.btnCancel.setOnClickListener(){
            val (id,alarm) = returnValue()
            if(alarm == null) {
                //Btn Cancel (For Creating)
                onBackPressed()
            }
            else if(alarm != null){
                databaseHelper.deleteData(id)
                Toast.makeText(this, "Successfully Deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleAlarm(context : Context, alarm : Alarm){
        var alarmManager : AlarmManager = context.getSystemService(AlarmManager::class.java)
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("alarmLabel", alarm.Label)
        val pendingIndent : PendingIntent = PendingIntent.getBroadcast(context, ((alarm.Hour).toInt() * 100 ) + alarm.Minute.toInt() , intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        //Setting alarm to ring
        var alarmCalendar : Calendar = Calendar.getInstance()
        alarmCalendar.set(Calendar.HOUR_OF_DAY, alarm.Hour.toInt())
        alarmCalendar.set(Calendar.MINUTE, alarm.Minute.toInt())
        alarmCalendar.set(Calendar.SECOND, 0)
        if(alarm.On){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmCalendar.timeInMillis, pendingIndent)
        }
        else{
            alarmManager.cancel(pendingIndent)
        }
    }
}