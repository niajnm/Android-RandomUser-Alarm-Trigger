package com.example.randomuser.AlarmClock

import Database.DatabaseHelper
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randomuser.HistoryAdapter
import com.example.randomuser.R
import kotlinx.android.synthetic.main.activity_alarm_create.*
import kotlinx.android.synthetic.main.activity_history.*
import java.util.*

class AlarmCreateActivity : AppCompatActivity() {

    lateinit var calender: Calendar
    lateinit var calender2: Calendar
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent
    lateinit var pi: PendingIntent
    private val CHANNELID = "111"
    var dialogView: View? = null
    var medicine: ArrayAdapter<String>? = null
    var flag = 0
    var alarmDataHelper : DatabaseHelper?=null
    var medicineName: String? = null
    var readableTime: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_create)

        //searchView = findViewById(R.id.search_id);
        val medicinearray = resources.getStringArray(R.array.medicine_name)
        medicine = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, medicinearray)
        autoCompleteText_id.threshold = 1
        autoCompleteText_id.setAdapter(medicine)

        alarmDataHelper = DatabaseHelper(this)
        createNotificationChannel()

        textTime_id.setOnClickListener {
            showTimePicker1()
        }

        textTime2_id.setOnClickListener {
            showTimePicker2()
        }

        setalarmbutton_id.setOnClickListener {

            if (flag == 0) {
                setAlarm1()

            } else if (flag == 2) {
                setAlarm1()
                setAlarm2()
            } else {

            }
        }

        btn1.setOnClickListener {
            textTime2_id.isVisible = false
            textTime3_id.isVisible = false
            flag = 0
        }
        btn2.setOnClickListener {
            textTime2_id.isVisible = true
            textTime3_id.isVisible = false
            flag = 2
        }
        btn3.setOnClickListener {
            textTime2_id.isVisible = true
            textTime3_id.isVisible = true
            flag = 3

        }
    }


    private fun showTimePicker1() {
        //--------< open_TimePickerDialog() >--------
        val hourOfDay = 0
        val minute = 0
        val is24HourView = false

        //Theme_Holo_Light_Dialog
        //Theme_Holo_Light_DarkActionBar  //*Top Position
        val _timePickerDialog = TimePickerDialog(
            this, android.R.style.Theme_Holo_Light_Dialog,
            { timePicker, i, i1 -> //*Return values
                if (i == 12 && i1 > 0) {

                    readableTime = String.format("%02d", i) + " : " + String.format(
                        "%02d",
                        i1
                    ) + "PM"
                    textTime_id.text = readableTime

                } else if (i == 0) {
                    readableTime =
                        String.format("%02d", i + 12) + " : " + String.format(
                            "%02d",
                            i1
                        ) + "AM"
                    textTime_id.text = readableTime
                } else if (i > 12) {
                    readableTime =
                        String.format("%02d", i - 12) + " : " + String.format(
                            "%02d",
                            i1
                        ) + "PM"
                    textTime_id.text = readableTime
                } else {
                    readableTime = String.format("%02d", i) + " : " + String.format(
                        "%02d",
                        i1
                    ) + "AM"
                    textTime_id.text = readableTime
                }
                calender = Calendar.getInstance()
                calender.set(Calendar.HOUR_OF_DAY, i)
                calender.set(Calendar.MINUTE, i1)
                calender.set(Calendar.SECOND, 0)
                calender.set(Calendar.MILLISECOND, 0)

                Toast.makeText(this, "i=$i i1=$i1", Toast.LENGTH_SHORT).show()
            }, hourOfDay, minute, is24HourView
        )
        _timePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _timePickerDialog.setTitle("Select a Time")
        _timePickerDialog.show()
    }

    private fun showTimePicker2() {
        //--------< open_TimePickerDialog() >--------
        val hourOfDay = 0
        val minute = 0
        val is24HourView = false

        //Theme_Holo_Light_Dialog
        //Theme_Holo_Light_DarkActionBar
        val _timePickerDialog = TimePickerDialog(
            this, android.R.style.Theme_Holo_Light_Dialog,
            { timePicker, i, i1 -> //*Return values
                if (i == 12 && i1 > 0) {
                    textTime2_id.text =
                        String.format("%02d", i) + " : " + String.format(
                            "%02d",
                            i1
                        ) + "PM"
                } else if (i == 0) {
                    textTime2_id.text =
                        String.format("%02d", i + 12) + " : " + String.format(
                            "%02d",
                            i1
                        ) + "AM"
                } else if (i > 12) {
                    textTime2_id.text =
                        String.format("%02d", i - 12) + " : " + String.format(
                            "%02d",
                            i1
                        ) + "PM"
                } else {
                    textTime2_id.text = String.format("%02d", i) + " : " + String.format(
                        "%02d",
                        i1
                    ) + "AM"
                }

                Toast.makeText(this, "i=$i i1=$i1", Toast.LENGTH_SHORT).show()
            }, hourOfDay, minute, is24HourView
        )
        _timePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _timePickerDialog.setTitle("Select a Time")
        _timePickerDialog.show()
        //--------</ open_TimePickerDialog() >--------
        calender2 = Calendar.getInstance()
        calender2.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calender2.set(Calendar.MINUTE, minute)
        calender2.set(Calendar.SECOND, 0)
        calender2.set(Calendar.MILLISECOND, 0)

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
//        pi = PendingIntent.getBroadcast(this, 2, intent, 0)
//
//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calender2.timeInMillis,
//            24 * 60 * 60 * 1000 * 7,
//            pi
//        )
//
//        Toast.makeText(this, "Alarm set succesfully", Toast.LENGTH_SHORT).show()
//    }
    //  }

    private fun repeatingAlarm2() {

        val thuReq: Long = Calendar.getInstance().timeInMillis + 1
        var repReqCode = thuReq.toInt()

        val intent = Intent(this, AlarmReceiver::class.java)
//        intent.action = "okay"
//        val time
//        intent.putExtra("time", time)
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)

        val pendingIntent = PendingIntent.getBroadcast(this, repReqCode, intent, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calender.timeInMillis,
            // Interval one day
            24 * 60 * 60 * 1000,
            pendingIntent
        )
    }

    private fun setAlarm1() {
        val alarmTimeMilsec = calender.timeInMillis.toInt()
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val thuReq: Long = Calendar.getInstance().timeInMillis + 1
        var repReqCode = thuReq.toInt()
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        val pendingIntent = PendingIntent.getBroadcast(this, repReqCode, intent, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calender.timeInMillis,
            24 * 60 * 60 * 1000,
            pendingIntent
        )
        val status ="everyday"
      alarmDataHelper?.insertAlarmData(medicineName,readableTime,alarmTimeMilsec,repReqCode,status)
        Toast.makeText(this, "Alarm set succesfully", Toast.LENGTH_SHORT).show()

    }

    private fun setAlarm2() {

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val thuReq: Long = Calendar.getInstance().timeInMillis + 1
        var repReqCode = thuReq.toInt()
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        val pendingIntent = PendingIntent.getBroadcast(this, repReqCode, intent, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calender2.timeInMillis,
            24 * 60 * 60 * 1000,
            pendingIntent
        )
//        pi = PendingIntent.getBroadcast(this, 1, intent, 0)
//
//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calender2.timeInMillis,
//            24*60*60*1000,
//            pi
//        )
        Toast.makeText(this, "Alarm set succesfully", Toast.LENGTH_SHORT).show()
    }

    private fun cencelAlarm() {
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        if (alarmManager == null) {
            alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        }
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Alarm canceled", Toast.LENGTH_SHORT).show()
    }

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