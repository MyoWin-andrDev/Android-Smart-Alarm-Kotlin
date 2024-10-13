package it.ezzie.smartalarminkotlin

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        private var DATABASE_NAME: String = "Alarm.db"
        private var DATABASE_VERSION: Int = 1;
        private var TABLE_NAME : String = "AlarmTable"
        private var COLUMN_ID : String = "Id"
        private var COLUMN_HOUR : String = "Hour"
        private var COLUMN_MINUTE : String = "Minute"
        private var COLUMN_DAY : String = "Day"
        private var COLUMN_UNIT : String = "Unit"
        private var COLUMN_ON : String = "AlarmOn"
        private var COLUMN_LABEL : String = "Label"


    }
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY = "CREATE TABLE $TABLE_NAME ( " +
                "$COLUMN_ID INTEGER PRIMARY KEY UNIQUE ," +
                "$COLUMN_HOUR TEXT, " +
                "$COLUMN_MINUTE TEXT, " +
                "$COLUMN_DAY TEXT, " +
                "$COLUMN_UNIT TEXT, " +
                "$COLUMN_ON TEXT, " +
                "$COLUMN_LABEL TEXT )"
        db?.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE_QUERY)
        onCreate(db)
    }

    fun createData(alarm : Alarm){
        val db = writableDatabase
        val value = ContentValues()
        value.put(COLUMN_HOUR, alarm.Hour)
        value.put(COLUMN_MINUTE, alarm.Minute)
        value.put(COLUMN_DAY, alarm.Day)
        value.put(COLUMN_UNIT, alarm.Unit)
        value.put(COLUMN_LABEL, alarm.Label)
        value.put(COLUMN_ON, alarm.On)
        db.insert(TABLE_NAME, null, value)
        db.close()
    }

    fun getAllData () : List<Alarm>{
        val alarmList = arrayListOf<Alarm>()
        val db = readableDatabase
        val READ_QUERY = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(READ_QUERY, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val hour = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOUR))
            val minute = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MINUTE))
            val day = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY))
            val unit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIT))
            val label = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LABEL))
            val on : Boolean = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ON)) == 1
            val alarm = Alarm(id, hour, minute, day, unit, label, on)
            alarmList.add(alarm)
        }
        cursor.close()
        db.close()
        return alarmList
    }

    fun deleteData(alarmId : Int){
        val db: SQLiteDatabase = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(alarmId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    @SuppressLint("Recycle")
    fun getAlarmById(id: Int?): Alarm? {
        val db = readableDatabase
        val READ_ID_QUERY = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(READ_ID_QUERY, arrayOf(id.toString()))
        return if (cursor.moveToFirst()) {
            val hour = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOUR))
            val minute = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MINUTE))
            val day = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY))
            val unit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIT))
            val label = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LABEL))
            val on = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ON)) == 1
            Alarm(id!!, hour, minute, day, unit, label, on) // Use !! if you're sure id is not null
        } else {
            null // Return null if no alarm found
        }.also {
            cursor.close() // Ensure cursor is closed
            db.close() // Close database
        }
    }


    fun updateData(alarm : Alarm){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(alarm.id.toString())
        val value = ContentValues()
        value.put(COLUMN_HOUR, alarm.Hour)
        value.put(COLUMN_MINUTE, alarm.Minute)
        value.put(COLUMN_DAY, alarm.Day)
        value.put(COLUMN_UNIT, alarm.Unit)
        value.put(COLUMN_LABEL, alarm.Label)
        value.put(COLUMN_ON, alarm.On)
        db.update(TABLE_NAME, value, whereClause, whereArgs)
        db.close()
    }
    fun updateSwitch( boo : Boolean ,alarm: Alarm){
        var db = writableDatabase
        val whereClause = "$COLUMN_ID"
        val whereArgus = arrayOf<String>(alarm.toString())
        var value = ContentValues()
        value.put(COLUMN_ON, boo)
        db.update(TABLE_NAME,value,whereClause,whereArgus)
        db.close()
    }
}