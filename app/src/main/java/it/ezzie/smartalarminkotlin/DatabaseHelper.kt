package it.ezzie.smartalarminkotlin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private var DATABASE_NAME = "Alarm.db"
        private var DATABASE_VERSION = 1
        private var TABLE_NAME = "AlarmTable"
        private var COLUMN_ID = "ID"
        private var COLUMN_HOUR = "Hour"
        private var COLUMN_MINUTE = "Minute"
        private var COLUMN_UNIT = "Unit"
        private var COLUMN_LABEL = "Label"
        private var COLUMN_ON = "ON"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_TABLE_QUERY = "CREATE TABLE $TABLE_NAME {" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT" +
                "$COLUMN_HOUR TEXT" +
                "$COLUMN_MINUTE TEXT" +
                "$COLUMN_UNIT TEXT" +
                "$COLUMN_LABEL TEXT" +
                "$COLUMN_ON TEXT"
        db?.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        var DROP_TABLE_QUERY = "DROP $DATABASE_NAME IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE_QUERY)
        onCreate(db)
        db?.close()
    }

    fun createAlarm(alarm : Alarm){
        var db = writableDatabase
        var values = ContentValues()
        values.put(COLUMN_HOUR, alarm.Hour)
        values.put(COLUMN_MINUTE, alarm.Minute)
        values.put(COLUMN_UNIT, alarm.Unit)
        values.put(COLUMN_LABEL, alarm.Label)
        values.put(COLUMN_ON, alarm.On)
        db.insert(TABLE_NAME,null,values)
        db.close()
    }
    fun getAllData() : List<Alarm>{
        var alarmList = arrayListOf<Alarm>()
        var db = readableDatabase
        var READ_DATA_QUERY = "SELECT * FROM $TABLE_NAME"
        var cursor = db.rawQuery(READ_DATA_QUERY,null)
        while (cursor.moveToNext()){
            var id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            var hour = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HOUR))
            var minute = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MINUTE))
            var unit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIT))
            var label = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LABEL))
            var on = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ON)) as Boolean
            var alarm = Alarm(id,hour,minute,unit,label,on)
            alarmList.add(alarm)
        }
        cursor.close()
        db.close()
        return alarmList
    }
    fun deleteData(alarmId : Int){
        var db = writableDatabase
        var whereClause = "$COLUMN_ID = ?"
        var whereArgus = arrayOf<String>(alarmId as String)
        db.delete(TABLE_NAME, whereClause, whereArgus)
        db.close()
    }
    fun getAlarmById(alarmId: Int) : Alarm {
        var db = readableDatabase
        var READ_DATA_QUERY ="SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $alarmId"
        var cursor = db.rawQuery(READ_DATA_QUERY, null)
        cursor.moveToFirst()
        var id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        var hour = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HOUR))
        var minute = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MINUTE))
        var unit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIT))
        var label = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LABEL))
        var on = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ON)) as Boolean
        var alarm = Alarm(id,hour,minute,unit,label,on)
        cursor.close()
        db.close()
        return alarm
    }
    fun updateData(alarm: Alarm){
        var db = writableDatabase
        var values = ContentValues()
        values.put(COLUMN_ID, alarm.id)
        values.put(COLUMN_HOUR, alarm.Hour)
        values.put(COLUMN_MINUTE, alarm.Minute)
        values.put(COLUMN_UNIT, alarm.Unit)
        values.put(COLUMN_LABEL, alarm.Label)
        values.put(COLUMN_ON, alarm.On)
        var whereClause = "$COLUMN_ID = ?"
        var whereArgs = arrayOf<String>(alarm.id as String)
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }
}