package it.ezzie.smartalarminkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.ezzie.smartalarminkotlin.databinding.AdapterAlarmBinding

class AlarmAdapter(private var context : Context, private var alarmList : List<Alarm> , var alarmEdit: AlarmClickedListener ) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    class AlarmViewHolder(var binding: AdapterAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = AdapterAlarmBinding.inflate(layoutInflater,parent,false)
        return AlarmViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }
    fun interface AlarmClickedListener{
        fun onAlarmClick(alarm: Alarm);
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        var databaseHelper = DatabaseHelper(context)
        val alarm = alarmList[position]
        holder.binding.alarmHour.text = alarm.Hour.toString()
        holder.binding.alarmMinute.text = alarm.Minute.toString()
        holder.binding.alarmLabel.text = alarm.Label
        holder.binding.alarmUnit.text = alarm.Unit

        //AM/PM View
        if (alarm.Unit == "AM") {
            holder.binding.imageView.setImageResource(R.drawable.ic_sun)
        } else if (alarm.Unit == "PM") {
            holder.binding.imageView.setImageResource(R.drawable.ic_moon)
        }
        holder.binding.listLinear.setOnClickListener{
            alarmEdit.onAlarmClick(alarm)
        }
        //Init Alarm Switch UI
        val toggleSwitch = holder.binding.alarmSwitch
        if(toggleSwitch.isChecked){
            databaseHelper.updateSwitch(true, alarm)
        }
        else{
            databaseHelper.updateSwitch(false, alarm)
        }
    }

}