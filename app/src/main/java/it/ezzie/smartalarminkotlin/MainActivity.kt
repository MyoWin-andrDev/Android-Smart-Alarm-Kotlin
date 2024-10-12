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
    private var REQUEST_CODE  = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDatabase();
        initUI();
        initListener();
    }
    fun initDatabase(){
        databaseHelper = DatabaseHelper(this)
        alarmList = databaseHelper.getAllData()
    }
    fun initUI(){
        binding.recyclerView.adapter = AlarmAdapter(this,alarmList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
    fun initListener(){
        binding.floatingBtn.setOnClickListener {
            var intent = Intent(this, EditAlarm::class.java)
            startActivityForResult(intent,REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }
}