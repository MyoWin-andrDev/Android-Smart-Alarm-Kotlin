package it.ezzie.smartalarminkotlin

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
        var CREATE_TABLE_QUERY = "CREATE TABLE $TABLE_NAME { " +
                "$COLUMN_HOUR INTEGER PRIMARY KEY AUTO INCREMENT" +
                "$COLUMN_HOUR TEXT" +
                "$COLUMN_MINUTE TEXT" +
                "$COLUMN_DAY TEXT" +
                "$COLUMN_UNIT TEXT" +
                "$COLUMN_ON TEXT" +
                "$COLUMN_LABEL TEXT"
        db?.execSQL(CREATE_TABLE_QUERY)
        db?.close()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        var DROP_TABLE_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE_QUERY)
        onCreate(db)
    }

    fun createData(alarm : Alarm){
        var db = writableDatabase
        var value = ContentValues()
        value.put(COLUMN_ID, alarm.id)
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
        var alarmList = arrayListOf<Alarm>()
        var db = readableDatabase
        var READ_QUERY = "SELECT * FROM $TABLE_NAME"
        var cursor = db.rawQuery(READ_QUERY, null)
        while (cursor.moveToNext()){
            var id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            var
        }
    }


}