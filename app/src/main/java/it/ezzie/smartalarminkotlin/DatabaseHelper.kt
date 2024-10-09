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
        private var COLUMN_UNIT : String = "Unit"
        private var COLUMN_ON : String = "AlarmOn"
        private var COLUMN_LABEL : String = "Label"


    }
    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


}