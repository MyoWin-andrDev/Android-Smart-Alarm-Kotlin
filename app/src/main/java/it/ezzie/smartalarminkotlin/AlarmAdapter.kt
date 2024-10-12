package it.ezzie.smartalarminkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.ezzie.smartalarminkotlin.databinding.AdapterAlarmBinding

class AlarmAdapter(context : Context, alarmList : List<Alarm> ) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    class AlarmViewHolder(binding : AdapterAlarmBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
       var layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        var binding = AdapterAlarmBinding.inflate(layoutInflater)
        return AlarmViewHolder(binding)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}