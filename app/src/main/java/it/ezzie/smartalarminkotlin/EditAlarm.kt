package it.ezzie.smartalarminkotlin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import it.ezzie.smartalarminkotlin.databinding.ActivityEditAlarmBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class EditAlarm : AppCompatActivity() {
    private lateinit var binding : ActivityEditAlarmBinding
    private var calendar : Calendar = Calendar.getInstance()
    private lateinit var databaseHelper: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditAlarmBinding.inflate(layoutInflater)
        databaseHelper = DatabaseHelper(this)
        setContentView(binding.root)
        initTimePicker();
    }
    fun initTimePicker(){
        lateinit var unit : String
        //Init TimePicker
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
           unit = SimpleDateFormat("a").format(calendar.time)
           if(totalResultMinute < 0) {
               resultHour = -resultHour
               resultMinute = -resultMinute
           }
           binding.timeCount.text = "Your alarm will ring in $resultHour hr $resultMinute min"
       }
        //Init Btn
        //Btn OK
        binding.btnOK.setOnClickListener(){
            var hour = calendar.get(Calendar.HOUR_OF_DAY).toString()
            var minute = SimpleDateFormat("mm").format(calendar.time)
            var label = binding.alarmEditTxt.text.toString()
            var on = true
            var alarm = Alarm( null ,hour, minute, null, unit, label, on)
            databaseHelper.createData(alarm)
            finish()
        }
        //Btn Cancel
        binding.btnCancel.setOnClickListener(){
            onBackPressed()
        }
    }
}