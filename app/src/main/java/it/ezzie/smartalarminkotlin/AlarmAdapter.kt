package it.ezzie.smartalarminkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.ezzie.smartalarminkotlin.databinding.AdapterAlarmBinding

class AlarmAdapter(private var context : Context, private var alarmList : List<Alarm> ) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    class AlarmViewHolder(var binding: AdapterAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        var binding = AdapterAlarmBinding.inflate(layoutInflater,parent,false)
        return AlarmViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }
    fun setAlarmList(alarmList: List<Alarm>) {
        this.alarmList = alarmList
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        var databaseHelper = DatabaseHelper(context)
        var alarm = alarmList.get(position)
        holder.binding.alarmHour.setText(alarm.Hour.toString())
        holder.binding.alarmMinute.setText(alarm.Minute.toString())
        holder.binding.alarmLabel.setText(alarm.Label)
        holder.binding.alarmUnit.setText(alarm.Unit)

        //AM/PM View
        if (alarm.Unit.equals("AM")) {
            holder.binding.imageView.setImageResource(R.drawable.ic_sun)
        } else if (alarm.Unit.equals("PM")) {
            holder.binding.imageView.setImageResource(R.drawable.ic_moon)
        }
        //Init Alarm Switch UI


    }

}