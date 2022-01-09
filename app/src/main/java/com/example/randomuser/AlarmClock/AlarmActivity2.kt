package com.example.randomuser.AlarmClock

import Database.DatabaseHelper
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randomuser.R
import kotlinx.android.synthetic.main.activity_alarm2.*
import kotlinx.android.synthetic.main.activity_history.*
import java.util.*

class AlarmActivity2 : AppCompatActivity() {

    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm2)

        floating_button_Id.setOnClickListener {
            val intent = Intent(this, AlarmCreateActivity::class.java)
            startActivity(intent)

            finish()
        }
        alarmDisplay()
    }
    fun alarmDisplay() {
        var databaseHelper = DatabaseHelper(this)
        val cursor = databaseHelper!!.displayAlarmData()
        val Rdata = databaseHelper!!.loadAlarmData(cursor)
        val alarmAdapter = AlarmAdapter(this, Rdata)
        alarmRecycler_id.adapter = alarmAdapter
        alarmRecycler_id.layoutManager = LinearLayoutManager(this)

    }

}