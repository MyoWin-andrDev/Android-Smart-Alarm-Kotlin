package it.ezzie.smartalarminkotlin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import it.ezzie.smartalarminkotlin.databinding.ActivityEditAlarmBinding
import java.util.Calendar

class EditAlarm : AppCompatActivity() {
    private lateinit var binding : ActivityEditAlarmBinding
    private var calendar : Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTimePicker();
    }
    fun initTimePicker(){
       binding.timePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
           calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
           calendar.set(Calendar.MINUTE, minute)
           var currentTotalMinute = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60 + Calendar.getInstance().get(Calendar.MINUTE)
           var totalSelectedMinute = hourOfDay * 60 + minute
           if(totalSelectedMinute < currentTotalMinute){
               totalSelectedMinute += 1440
           }
           var totalResultMinute = totalSelectedMinute - currentTotalMinute
           var resultHour = totalResultMinute / 60
           var resultMinute = totalResultMinute % 60
           if(totalResultMinute < 0) {
               resultHour = -resultHour
               resultMinute = -resultMinute
           }
           binding.timeCount.text = "Your alarm will ring in $resultHour hr $resultMinute min"
       }
    }
}