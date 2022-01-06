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
    private val CHANNELID = "111"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm2)

        createNotificationChannel()

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








    //   private fun setAlarm() {
//        calender2.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
//        if (calender2.timeInMillis < System.currentTimeMillis()) {
//            calender2.add(Calendar.DAY_OF_YEAR, 7)
//        }
//        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(this, AlarmReceiver::class.java)
//        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
//
//
//
//        pi = PendingIntent.getBroadcast(this, 2, intent, 0)
//
//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calender2.timeInMillis,
//            24 * 60 * 60 * 1000 * 7,
//            pi
//        )
//
//
//        Toast.makeText(this, "Alarm set succesfully", Toast.LENGTH_SHORT).show()
//    }



    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "Alarmclock Channel"
            val description = " Reminder Alarm manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNELID, name, importance)
            notificationChannel.description = description
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}