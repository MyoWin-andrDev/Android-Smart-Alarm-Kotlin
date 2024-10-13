package it.ezzie.smartalarminkotlin


import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import it.ezzie.smartalarminkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var alarmList : List<Alarm>
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var adapter : AlarmAdapter
    private var REQUEST_CODE = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        databaseHelper = DatabaseHelper(this)
        setContentView(binding.root)
        initUI();
        initListener();
    }

    override fun onResume() {
        super.onResume()
        initUI()
    }

    private fun initUI(){
        alarmList = databaseHelper.getAllData()
        adapter = AlarmAdapter(this, alarmList, alarmEdit = {alarm ->
            val intent = Intent(this , EditAlarm::class.java)
            intent.putExtra("alarmId", alarm.id)
            startActivity(intent)
        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
    private fun initListener(){
        binding.floatingBtn.setOnClickListener {
            val intent = Intent(this, EditAlarm::class.java)
            startActivityForResult(intent,REQUEST_CODE)
        }
    }



}