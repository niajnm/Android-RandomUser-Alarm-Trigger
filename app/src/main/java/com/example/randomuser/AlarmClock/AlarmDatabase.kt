package com.example.randomuser.AlarmClock

import Database.DatabaseHelper
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class AlarmDatabase  {

    companion object {
        private const val DATABASE_NAME = "Users_DB"
        private const val TABLE_NAME = "User_History"
        private const val ALARM_TABLE_NAME = "Alarm_history"

        private const val TITLE = "med_name"
        private const val CTIME = "c_time"
        private const val REQC = "request_code"
        private const val FLAG = "flag"
        private const val WEEKDAYS = "week_days"
        private const val Time = "alarm_time"

        private const val ID = "id_"
        private const val DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME
        private const val VERSION_NUM = 3

    }







    }



