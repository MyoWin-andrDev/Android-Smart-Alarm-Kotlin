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
    private var REQUEST_CODE  = 123
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

    fun initUI(){
        alarmList = databaseHelper.getAllData()
        adapter = AlarmAdapter(this, alarmList, alarmEdit = {alarm ->
            var intent = Intent(this , EditAlarm::class.java)
            intent.putExtra("alarmId", alarm.id)

        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
    fun initListener(){
        binding.floatingBtn.setOnClickListener {
            var intent = Intent(this, EditAlarm::class.java)
            startActivityForResult(intent,REQUEST_CODE)
        }
    }



}