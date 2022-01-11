package com.example.randomuser.AlarmClock

import Database.DatabaseHelper
import android.app.*
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randomuser.R
import kotlinx.android.synthetic.main.activity_alarm2.*

class AlarmActivity2 : AppCompatActivity() {
    lateinit var alarmManager: AlarmManager
    lateinit var context: Context
    lateinit var pendingIntent: PendingIntent
    lateinit var alarmAdapter: AlarmAdapter


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
        alarmAdapter = AlarmAdapter(this, Rdata)
        alarmRecycler_id.adapter = alarmAdapter
        alarmRecycler_id.layoutManager = LinearLayoutManager(this)
        alarmAdapter.notifyDataSetChanged()
        alarmRecycler_id.invalidate()


    }

    fun deleteAlarm(magicKey: Int) {
        var databaseHelper = DatabaseHelper(this)
        val cursor = databaseHelper!!.loadAlarmDatadDelete(magicKey)
        val rCodeList = databaseHelper!!.loadDeleteAlarmData(cursor)

        for (i in rCodeList.indices) {

            val dKey = rCodeList[i].requestCode
            val dKey2 = rCodeList[i].requestCode2

            cencelAlarm(dKey!!)
            cencelAlarm(dKey2!!)
        }

        databaseHelper?.deleteAlarmData(magicKey)

          alarmDisplay()


    }

    fun offAlarm(magicKey: Int) {
        var databaseHelper = DatabaseHelper(this)
        val cursor = databaseHelper!!.loadAlarmDatadDelete(magicKey)
        val rCodeList = databaseHelper!!.loadDeleteAlarmData(cursor)

        for (i in rCodeList.indices) {

            val dKey = rCodeList[i].requestCode
            val dKey2 = rCodeList[i].requestCode2

            cencelAlarm(dKey!!)
            cencelAlarm(dKey2!!)
        }

    }

    private fun cencelAlarm(Dkey: Int) {
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, Dkey, intent, 0)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Alarm canceled", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        alarmDisplay()
    }


}