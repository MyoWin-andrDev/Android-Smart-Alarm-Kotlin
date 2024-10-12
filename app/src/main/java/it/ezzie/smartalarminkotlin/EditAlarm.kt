package it.ezzie.smartalarminkotlin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import it.ezzie.smartalarminkotlin.databinding.ActivityEditAlarmBinding

class EditAlarm : AppCompatActivity() {
    private lateinit var binding : ActivityEditAlarmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}