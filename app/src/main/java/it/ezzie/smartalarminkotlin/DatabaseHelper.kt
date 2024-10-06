package it.ezzie.smartalarminkotlin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private var DATABASE_NAME = "Alarm.db"
        private var DATABASE_VERSION = 1
        private var TABLE_NAME = "AlarmTable"
        private var COLUMN_ID = "ID"
        private var COLUMN_TITLE = "Title"
        private var COLUMN_DESCRIPTION = "Description"
        private var COLUMN_DATE = "Date"
        private var COLUMN_TIME = "Time"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}