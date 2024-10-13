package it.ezzie.smartalarminkotlin

import android.annotation.SuppressLint
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
    private lateinit var unit: String
    private var id : Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditAlarmBinding.inflate(layoutInflater)
        databaseHelper = DatabaseHelper(this)
        setContentView(binding.root)
        initTimePicker()
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
            unit = SimpleDateFormat("a").format(calendar.time)
            if (totalResultMinute < 0) {
                resultHour = -resultHour
                resultMinute = -resultMinute
            }
            binding.timeCount.text = "Your alarm will ring in $resultHour hr $resultMinute min"
        }

    }
}