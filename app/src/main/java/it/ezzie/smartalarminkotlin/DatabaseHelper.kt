package it.ezzie.smartalarminkotlin

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

    public fun createData(){

    }


}