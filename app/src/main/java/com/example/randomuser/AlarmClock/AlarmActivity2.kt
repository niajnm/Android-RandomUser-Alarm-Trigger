package com.example.randomuser.AlarmClock

import Database.DataModel
import Database.DatabaseHelper
import android.app.*
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randomuser.R
import kotlinx.android.synthetic.main.activity_alarm2.*
import java.util.*
import kotlin.concurrent.thread

class AlarmActivity2 : AppCompatActivity() {
    lateinit var alarmManager: AlarmManager
    lateinit var context: Context
    lateinit var pendingIntent: PendingIntent
    lateinit var alarmAdapter: AlarmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm2)
        context = this
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
//
//    @DelicateCoroutinesApi
//    fun onAlarm(magicKey: Int) {
//
//        GlobalScope.launch(Dispatchers.IO) {
//            var databaseHelper = DatabaseHelper(context)
//            val cursor = databaseHelper.loadAlarmDataOn(magicKey)
//            val rCodeList = databaseHelper.OnAlarmData(cursor)
//
//            for (i in rCodeList.indices) {
//
//                var cTime = rCodeList[i].alarmTMilsec
//                val cTime2 = rCodeList[i].alarmTMilsec2
//
//                var newtime = rCodeList[i].alarmTMilsec!!
//                var newtime2 = rCodeList[i].alarmTMilsec!!
//                var reqReqCode = rCodeList[i].requestCode!!
//                var reqReqCode2 = rCodeList[i].requestCode2!!
//
//                val currentTime = System.currentTimeMillis()
//                Log.d("newtime", "getfrom $newtime")
//                if (reqReqCode2 == 0) {
//                    while (newtime < currentTime) {
//
//                        newtime += 86400000
//                        Log.d("newtime", "updatetime $newtime")
//                    }
//                    databaseHelper!!.alarmUpdate(cTime, newtime)
//                    alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//                    val intent = Intent(context, AlarmReceiver::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK
//                    val pendingIntent = PendingIntent.getBroadcast(context, reqReqCode, intent, 0)
//
//                    alarmManager.setRepeating(
//                        AlarmManager.RTC_WAKEUP, newtime,
//                        24 * 60 * 60 * 1000,
//                        pendingIntent
//                    )
//                    Log.d("newtime", "onAlarm $newtime")
//
//
//                } else {
//                    while (newtime < currentTime) {
//
//                        newtime += 86400000
//                        Log.d("newtime", "updatetime $newtime")
//                    }
//                    databaseHelper!!.alarmUpdate(cTime, newtime)
//
//
//                    while (newtime2 < currentTime) {
//
//                        newtime2 += 86400000
//                        Log.d("Onetime", "updatetime $newtime2")
//                    }
//
//                    databaseHelper!!.alarmUpdateTwice(cTime2, newtime2)
//
//                    alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//                    val intent = Intent(context, AlarmReceiver::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK
//                    val pendingIntent = PendingIntent.getBroadcast(context, reqReqCode, intent, 0)
//
//                    alarmManager.setRepeating(
//                        AlarmManager.RTC_WAKEUP, newtime,
//                        24 * 60 * 60 * 1000,
//                        pendingIntent
//                    )
//                    val pi = PendingIntent.getBroadcast(context, reqReqCode2, intent, 0)
//                    alarmManager.setRepeating(
//                        AlarmManager.RTC_WAKEUP, newtime2,
//                        24 * 60 * 60 * 1000,
//                        pi
//                    )
//                    Log.d("Twotime", "onAlarm $newtime")
//
//                }
//
//            }
//        }
//        Toast.makeText(context, "Alarm on", Toast.LENGTH_SHORT).show()
//
//    }

    fun onAlarm(magicKey: Int) {
        var databaseHelper = DatabaseHelper(context)
        val cursor = databaseHelper.loadAlarmDataOn(magicKey)
        val rCodeList = databaseHelper.OnAlarmData(cursor)


        if (rCodeList.size == 1) {
            singleAlarmOn(rCodeList)

        } else {
            multiAlarmOn(rCodeList)

        }

    }

    private fun singleAlarmOn(rCodeList: ArrayList<DataModel>) {
        var databaseHelper = DatabaseHelper(context)
        thread {
            for (i in rCodeList.indices) {

                var cTime = rCodeList[i].alarmTMilsec
                val cTime2 = rCodeList[i].alarmTMilsec2
                var newtime = rCodeList[i].alarmTMilsec!!
                var newtime2 = rCodeList[i].alarmTMilsec2!!
                var reqReqCode = rCodeList[i].requestCode!!
                var reqReqCode2 = rCodeList[i].requestCode2!!

                val currentTime = System.currentTimeMillis()
                Log.d("newtime", "getfrom $newtime")
                if (reqReqCode2 == 0) {
                    while (newtime < currentTime) {
                        newtime += 86400000
                        Log.d("newtime", "updatetime $newtime")
                    }
                    databaseHelper!!.alarmUpdate(cTime, newtime)
                    alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val intent = Intent(context, AlarmReceiver::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                    val pendingIntent = PendingIntent.getBroadcast(context, reqReqCode, intent, 0)

                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP, newtime,
                        24 * 60 * 60 * 1000,
                        pendingIntent
                    )
                    Log.d("newtime", "onAlarm $newtime")

                } else {
                    while (newtime < currentTime) {

                        newtime += 86400000
                        Log.d("newtime", "updatetime $newtime")
                    }
                    databaseHelper!!.alarmUpdate(cTime, newtime)


                    while (newtime2 < currentTime) {

                        newtime2 += 86400000
                        Log.d("Onetime", "updatetime $newtime2")
                    }

                    databaseHelper!!.alarmUpdateTwice(cTime2, newtime2)

                    alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val intent = Intent(context, AlarmReceiver::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                    val pendingIntent = PendingIntent.getBroadcast(context, reqReqCode, intent, 0)

                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP, newtime,
                        24 * 60 * 60 * 1000,
                        pendingIntent
                    )
                    val pi = PendingIntent.getBroadcast(context, reqReqCode2, intent, 0)
                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP, newtime2,
                        24 * 60 * 60 * 1000,
                        pi
                    )
                    Log.d("Twotime", "onAlarm $newtime")

                }
            }
        }
        Toast.makeText(context, "Alarm on", Toast.LENGTH_SHORT).show()

    }

    private fun multiAlarmOn(rCodeList: ArrayList<DataModel>) {
        var databaseHelper = DatabaseHelper(context)
        thread {
            for (i in rCodeList.indices) {

                var cTime = rCodeList[i].alarmTMilsec
                val cTime2 = rCodeList[i].alarmTMilsec2

                var newtime = rCodeList[i].alarmTMilsec!!
                var newtime2 = rCodeList[i].alarmTMilsec2!!
                var reqReqCode = rCodeList[i].requestCode!!
                var reqReqCode2 = rCodeList[i].requestCode2!!

                val currentTime = System.currentTimeMillis()
                Log.d("newtime", "getfrom $newtime")
                if (reqReqCode2 == 0) {
                    while (newtime < currentTime) {

                        newtime += 86400000 * 7
                        Log.d("newtime", "updatetime $newtime")
                    }
                    databaseHelper!!.alarmUpdate(cTime, newtime)
                    alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val intent = Intent(context, AlarmReceiver::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                    val pendingIntent = PendingIntent.getBroadcast(context, reqReqCode, intent, 0)

                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP, newtime,
                        24 * 60 * 60 * 1000,
                        pendingIntent
                    )
                    Log.d("newtime", "onAlarm $newtime")


                } else {
                    while (newtime < currentTime) {

                        newtime += 86400000 * 7
                        Log.d("newtime", "updatetime $newtime")
                    }
                    databaseHelper!!.alarmUpdate(cTime, newtime)


                    while (newtime2 < currentTime) {

                        newtime2 += 86400000 * 7
                        Log.d("Onetime", "updatetime $newtime2")
                    }

                    databaseHelper!!.alarmUpdateTwice(cTime2, newtime2)

                    alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val intent = Intent(context, AlarmReceiver::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                    val pendingIntent = PendingIntent.getBroadcast(context, reqReqCode, intent, 0)

                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP, newtime,
                        24 * 60 * 60 * 1000 * 7,
                        pendingIntent
                    )
                    val pi = PendingIntent.getBroadcast(context, reqReqCode2, intent, 0)
                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP, newtime2,
                        24 * 60 * 60 * 1000 * 7,
                        pi
                    )
                    Log.d("Twotime", "onAlarm $newtime")

                }

            }
        }
        Toast.makeText(context, "Alarm on", Toast.LENGTH_SHORT).show()
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

    fun alarmDetails(time: String?, time2: String?, name: String?, name2: String?, days: String?) {
        val intent = Intent(this, AlarmDetailsActivity::class.java)
        intent.putExtra("alarmTime", time)
        intent.putExtra("alarmTime2", time2)
        intent.putExtra("alarmName", name)
        intent.putExtra("alarmName2", name2)
        intent.putExtra("alarmDays", days)
        startActivity(intent)

    }
}