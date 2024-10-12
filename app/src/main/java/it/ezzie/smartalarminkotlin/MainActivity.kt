package it.ezzie.smartalarminkotlin

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import it.ezzie.smartalarminkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var alarmList : List<Alarm>
    private lateinit var databaseHelper: DatabaseHelper
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

}