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
        initUpdateUI()
        initTimePicker()
    }

    @SuppressLint("SetTextI18n")
    private fun initUpdateUI() {
        id = intent?.getIntExtra("alarmId", -1)!!
        if (id != -1) {
            val alarm = databaseHelper.getAlarmById(id)
            if(alarm != null) {
                binding.timePicker.hour = Integer.parseInt(alarm.Hour)
                binding.timePicker.minute = Integer.parseInt(alarm.Minute)
                binding.alarmEditTxt.setText(alarm.Label)
                binding.btnOK.text = "Update"
                binding.btnCancel.text = "Delete"
                initListener(alarm)
            }
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
            unit = SimpleDateFormat("a").format(calendar.time)
            if (totalResultMinute < 0) {
                resultHour = -resultHour
                resultMinute = -resultMinute
            }
            binding.timeCount.text = "Your alarm will ring in $resultHour hr $resultMinute min"
        }

    }
    private fun initListener(alarm : Alarm){
        //Create Alarm
        binding.btnOK.setOnClickListener() {
            val hour = calendar.get(Calendar.HOUR_OF_DAY).toString()
            val minute = calendar.get(Calendar.MINUTE).toString()
            val label = binding.alarmEditTxt.text.toString()
            if (id == -1) {
                val alarmCreate = Alarm(id, hour, minute, null, unit, label, true)
                databaseHelper.createData(alarmCreate)
                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
                finish()
            } else if (id != -1) {
                if (hour != alarm.Hour || minute != alarm.Minute || label != alarm.Label) {
                    var alarmUpdate = Alarm(id, hour, minute, null, unit, label, true)
                    databaseHelper.updateData(alarmUpdate)
                    Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
        //Cancel or delete Alarm
        binding.btnCancel.setOnClickListener(){
            //Btn Cancel
            if(id == -1){
                onBackPressed()
            }
            else if(id != -1){
                databaseHelper.deleteData(id)
                Toast.makeText(this, "Successfully Deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
        }


    }
}