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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.randomuser.R
import kotlinx.android.synthetic.main.activity_alarm_create.*
import kotlinx.android.synthetic.main.alarm_picker_dialogue_layout.view.*
import java.time.DayOfWeek
import java.util.*
import kotlin.math.log

class AlarmCreateActivity : AppCompatActivity() {

    lateinit var calender: Calendar
    lateinit var calender2: Calendar
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent
    lateinit var pi: PendingIntent
    private val CHANNELID = "111"
    var dialogView: View? = null
    var medicine: ArrayAdapter<String>? = null
    private var flag = 0
    private var flag2 = 0
    var alarmDataHelper: DatabaseHelper? = null
    var medicineName: String? = null
    var readableTime: String? = null
    var readableTime_2: String? = null
    var flagCheckbox = 0

    var autogenerateKey: Int = 1

    var medicine1 = ""
    var medicine2 = ""
    var medicine3 = ""
    var medicine4 = ""
    private var sat = ""
    private var sun = ""
    private var mon = ""
    private var tue = ""
    private var wed = ""
    private var thu = ""
    private var fri = ""
    private val stringBuffer = StringBuffer()
    var medflag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_create)

        //searchView = findViewById(R.id.search_id);
        val medicinearray = resources.getStringArray(R.array.medicine_name)
        medicine = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, medicinearray)
        autoCompleteText_id.threshold = 1
        autoCompleteText_id.setAdapter(medicine)

        autoCompleteText2_id.threshold = 1
        autoCompleteText2_id.setAdapter(medicine)

        autoCompleteText3_id.threshold = 1
        autoCompleteText3_id.setAdapter(medicine)
        autoCompleteText4_id.threshold = 1
        autoCompleteText4_id.setAdapter(medicine)

        buttonAdd_id.setOnClickListener {
            medflag++

            if (medflag == 1) {
                autoCompleteText2_id.isVisible = true

            }
            if (medflag == 2) {
                autoCompleteText3_id.isVisible = true

            }
            if (medflag == 3) {
                autoCompleteText4_id.isVisible = true

            }
        }


        alarmDataHelper = DatabaseHelper(this)
        createNotificationChannel()

        textTime_id.setOnClickListener {
            showTimePicker1()
        }

        textTime2_id.setOnClickListener {
            showTimePicker2()
        }

        btn_everyday.setOnClickListener {
            flag = 0
            flag2 = 0
        }

        btn_customDay.setOnClickListener {
            flag2 = 5
            val builder1 = AlertDialog.Builder(this)
            val layoutInflater = LayoutInflater.from(this)
            dialogView = layoutInflater.inflate(R.layout.alarm_picker_dialogue_layout, null)
            builder1.setView(dialogView)
            builder1.setCancelable(true)
            val alert11: AlertDialog = builder1.create()
            alert11.show()

            val sharedPreferences = getSharedPreferences("my_sharedPreference", 0)
            val editor = sharedPreferences.edit()

            if (flagCheckbox > 0) {
                val satu = sharedPreferences.getBoolean("sat", true)
                dialogView!!.checkBox1.isChecked = satu
                val sund = sharedPreferences.getBoolean("sun", true)
                dialogView!!.checkBox2.isChecked = sund
                val mond = sharedPreferences.getBoolean("mon", true)
                dialogView!!.checkBox3.isChecked = mond
                val tues = sharedPreferences.getBoolean("tue", true)
                dialogView!!.checkBox4.isChecked = tues
                val wedn = sharedPreferences.getBoolean("wed", true)
                dialogView!!.checkBox5.isChecked = wedn
                val thus = sharedPreferences.getBoolean("thu", true)
                dialogView!!.checkBox6.isChecked = thus
                val frid = sharedPreferences.getBoolean("fri", true)
                dialogView!!.checkBox7.isChecked = frid
            }

            dialogView!!.confirm_button_id.setOnClickListener {
                stringBuffer.setLength(0)

                var key: Long = (System.currentTimeMillis()+12)
                autogenerateKey = key.toInt()

                Log.d("mykey", "key:"+autogenerateKey)

                if (dialogView!!.checkBox1.isChecked) {
                    sat = "7"
                    editor.putBoolean("sat", true)
                    editor.apply()
                    stringBuffer.append("Saturday ")
                } else {
                    editor.putBoolean("sat", false)
                    editor.apply()
                }
                if (dialogView!!.checkBox2.isChecked) {
                    sun = "1"
                    editor.putBoolean("sun", true)
                    editor.apply()
                    stringBuffer.append("Sunday ")

                } else {
                    editor.putBoolean("sun", false)
                    editor.apply()
                }
                if (dialogView!!.checkBox3.isChecked) {
                    mon = "2"
                    editor.putBoolean("mon", true)
                    editor.apply()
                    stringBuffer.append("Monday ")

                } else {
                    editor.putBoolean("mon", false)
                    editor.apply()
                }
                if (dialogView!!.checkBox4.isChecked) {
                    tue = "3"
                    editor.putBoolean("tue", true)
                    editor.apply()
                    stringBuffer.append("Tuesday ")

                } else {
                    editor.putBoolean("tue", false)
                    editor.apply()
                }
                if (dialogView!!.checkBox5.isChecked) {
                    wed = "4"
                    editor.putBoolean("wed", true)
                    editor.apply()
                    stringBuffer.append("Wednesday ")

                } else {
                    editor.putBoolean("wed", false)
                    editor.apply()
                }
                if (dialogView!!.checkBox6.isChecked) {
                    thu = "5"
                    editor.putBoolean("thu", true)
                    editor.apply()
                    stringBuffer.append("Thursday ")

                } else {
                    editor.putBoolean("thu", false)
                    editor.apply()
                }
                if (dialogView!!.checkBox7.isChecked) {
                    fri = "6"
                    editor.putBoolean("fri", true)
                    editor.apply()
                    stringBuffer.append("Friday ")

                } else {
                    editor.putBoolean("fri", false)
                    editor.apply()
                }

                flagCheckbox++

                btn_customDay.text = "Specific days :" + stringBuffer.toString()

                alert11.dismiss()
            }
        }

        setAlarmButton_id.setOnClickListener {

            medicine1 = autoCompleteText_id.text.toString()
            medicine2 = autoCompleteText2_id.text.toString()
            medicine3 = autoCompleteText3_id.text.toString()
            medicine4 = autoCompleteText4_id.text.toString()
            medicineName =  medicine1 +" "+medicine2+" "+medicine3+" "+medicine4


            if (flag == 0 && flag2 == 0) {
                setAlarm1()

            } else if (flag == 0 && flag2 == 5) {
                weekDayAlarm()

                val repReqCode = 1
                val reqReqCode2 = 0
                val totReadabletime = readableTime + " " + readableTime_2
                val status = stringBuffer.toString()
                val alarmTimeMilsec = calender.timeInMillis.toInt()

                alarmDataHelper?.insertAlarmData(
                    medicineName,
                    readableTime,
                    alarmTimeMilsec,
                    repReqCode,
                    reqReqCode2,
                    status,
                    autogenerateKey
                )

            } else if (flag == 2 && flag2 == 0) {
                setAlarm2()
            } else if (flag == 2 && flag2 == 5) {
                weekDayAlarm2()

                val repReqCode = 1
                val reqReqCode2 = 0
                val totReadabletime = readableTime + " " + readableTime_2
                val status = stringBuffer.toString()
                val alarmTimeMilsec = calender.timeInMillis.toInt()

                alarmDataHelper?.insertAlarmData(
                    medicineName,
                    totReadabletime,
                    alarmTimeMilsec,
                    repReqCode,
                    reqReqCode2,
                    status,
                    autogenerateKey
                )
            } else {
            }

            finish()
            val intent = Intent(this, AlarmActivity2::class.java)
            startActivity(intent)
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
        val hourOfDay = 0
        val minute = 0
        val is24HourView = false

        //Theme_Holo_Light_Dialog
        //Theme_Holo_Light_DarkActionBar  //*Top Position
        val _timePickerDialog = TimePickerDialog(
            this, android.R.style.Theme_Holo_Light_Dialog,
            { timePicker, i, i1 ->
                if (i == 12 && i1 > 0) {

                    readableTime = String.format("%02d", i) + ":" + String.format(
                        "%02d",
                        i1
                    ) + "PM"
                    textTime_id.text = readableTime

                } else if (i == 0) {
                    readableTime =
                        String.format("%02d", i + 12) + ":" + String.format(
                            "%02d",
                            i1
                        ) + "AM"
                    textTime_id.text = readableTime
                } else if (i > 12) {
                    readableTime =
                        String.format("%02d", i - 12) + ":" + String.format(
                            "%02d",
                            i1
                        ) + "PM"
                    textTime_id.text = readableTime
                } else {
                    readableTime = String.format("%02d", i) + ":" + String.format(
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
        val hourOfDay = 0
        val minute = 0
        val is24HourView = false

        val _timePickerDialog = TimePickerDialog(
            this, android.R.style.Theme_Holo_Light_Dialog,
            { timePicker, i, i1 -> //*Return values
                if (i == 12 && i1 > 0) {

                    readableTime_2 = String.format("%02d", i) + ":" + String.format(
                        "%02d",
                        i1
                    ) + "PM"
                    textTime2_id.text = readableTime_2

                } else if (i == 0) {
                    readableTime_2 =
                        String.format("%02d", i + 12) + ":" + String.format(
                            "%02d",
                            i1
                        ) + "AM"
                    textTime2_id.text = readableTime_2
                } else if (i > 12) {
                    readableTime_2 =
                        String.format("%02d", i - 12) + ":" + String.format(
                            "%02d",
                            i1
                        ) + "PM"
                    textTime2_id.text = readableTime_2
                } else {
                    readableTime_2 = String.format("%02d", i) + ":" + String.format(
                        "%02d",
                        i1
                    ) + "AM"
                    textTime2_id.text = readableTime_2
                }
                calender2 = Calendar.getInstance()
                calender2.set(Calendar.HOUR_OF_DAY, i)
                calender2.set(Calendar.MINUTE, i1)
                calender2.set(Calendar.SECOND, 0)
                calender2.set(Calendar.MILLISECOND, 0)

                Toast.makeText(this, "i=$i i1=$i1", Toast.LENGTH_SHORT).show()
            }, hourOfDay, minute, is24HourView
        )
        _timePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _timePickerDialog.setTitle("Select a Time")
        _timePickerDialog.show()
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


    private fun setAlarm1() {
        val alarmTimeMilsec = calender.timeInMillis.toInt()
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val thuReq: Long = Calendar.getInstance().timeInMillis + 1
        var reqReqCode = thuReq.toInt()
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        val pendingIntent = PendingIntent.getBroadcast(this, reqReqCode, intent, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calender.timeInMillis,
            24 * 60 * 60 * 1000,
            pendingIntent
        )
        val status = "everyday"
        val reqReqCode2 = 0
        alarmDataHelper?.insertAlarmData(
            medicineName,
            readableTime,
            alarmTimeMilsec,
            reqReqCode,
            reqReqCode2,
            status,
            autogenerateKey
        )
        Toast.makeText(this, "Alarm set succesfully", Toast.LENGTH_SHORT).show()

    }

    private fun setAlarm2() {

        val totReadabletime = readableTime + " " + readableTime_2

        val alarmTimeMilsec = calender.timeInMillis.toInt()
        val alarmTimeMilsec2 = calender2.timeInMillis.toInt()

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val thuReq: Long = Calendar.getInstance().timeInMillis + 1
        var repReqCode1 = thuReq.toInt()

        val intent = Intent(this, AlarmReceiver::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        val pendingIntent = PendingIntent.getBroadcast(this, repReqCode1, intent, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calender.timeInMillis,
            24 * 60 * 60 * 1000,
            pendingIntent
        )
        val thuReq2: Long = Calendar.getInstance().timeInMillis + 2
        var repReqCode2 = thuReq2.toInt()
        pi = PendingIntent.getBroadcast(this, repReqCode2, intent, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calender2.timeInMillis,
            24 * 60 * 60 * 1000,
            pi
        )
        val status = "everyday"
        alarmDataHelper?.insertAlarmData(
            medicineName,
            totReadabletime,
            alarmTimeMilsec,
            repReqCode1,
            repReqCode2,
            status,
            autogenerateKey
        )
        Toast.makeText(this, "Alarm set succesfully", Toast.LENGTH_SHORT).show()
    }

    private fun weekDayAlarm() {
        if (sat == "7") {
            repeatingAlarm(7, "Saturday")
            Log.d("tag", "saturday")
        }
        if (sun == "1") {
            repeatingAlarm(1, "Sunday")
            Log.d("tag", "sunday")
        }
        if (mon == "2") {
            repeatingAlarm(2, "Monday")
            Log.d("tag", "monday")
        }
        if (tue == "3") {
            repeatingAlarm(3, "Tuesday")
            Log.d("tag", "tuesday")
        }
        if (wed == "4") {
            repeatingAlarm(4, "Wednesday")
            Log.d("tag", "wednesday")
        }
        if (thu == "5") {
            repeatingAlarm(5, "Thursday")
        }
        if (fri == "6") {
            repeatingAlarm(6, "Friday")

            Log.d("tag", "friday")
        } else {
        }
    }

    private fun repeatingAlarm(dayOfWeek: Int, dayName: String) {

        val status = stringBuffer.toString()
        val alarmTimeMilsec = calender.timeInMillis.toInt()

        calender = Calendar.getInstance()
        calender.set(Calendar.DAY_OF_WEEK, dayOfWeek)
        if (calender.timeInMillis < System.currentTimeMillis()) {
            calender.add(Calendar.DAY_OF_YEAR, 7)
        }
        val thuReq: Long = Calendar.getInstance().timeInMillis + 6
        var repReqCode = thuReq.toInt()
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
//        intent.action = "okay"
//        val time
//        intent.putExtra("time", time)
        intent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK

        val pendingIntent = PendingIntent.getBroadcast(this, repReqCode, intent, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calender.timeInMillis,
            24 * 60 * 60 * 1000 * 7,
            pendingIntent
        )

        val reqReqCode2 = 0

        alarmDataHelper?.insertMultiAlarmData(
            medicineName,
            readableTime,
            alarmTimeMilsec,
            repReqCode,
            reqReqCode2,
            dayName,
            autogenerateKey
        )
        Toast.makeText(this, "Alarm set succesfully", Toast.LENGTH_SHORT).show()
    }

    private fun weekDayAlarm2() {
        if (sat == "7") {
            twiceRepeatingAlarm(7, 7,"Saturday")
            Log.d("tag", "saturday")
        }
        if (sun == "1") {

            twiceRepeatingAlarm(1, 1,"Sunday")
            Log.d("tag", "sunday")
        }
        if (mon == "2") {
            twiceRepeatingAlarm(2, 2,"Monday")
            Log.d("tag", "monday")
        }
        if (tue == "3") {
            twiceRepeatingAlarm(3, 3,"Tuesday")
            Log.d("tag", "tuesday")
        }
        if (wed == "4") {
            twiceRepeatingAlarm(4, 4,"Wednesday")
            Log.d("tag", "wednesday")
        }
        if (thu == "5") {
            twiceRepeatingAlarm(5, 5,"Thursday")
        }
        if (fri == "6") {
            twiceRepeatingAlarm(6, 6,"Friday")
            Log.d("tag", "friday")
        } else {
        }

    }

    private fun twiceRepeatingAlarm(dayOfWeek: Int, dayOfWeek2: Int, dayName: String) {

        val totReadabletime = readableTime +" "+ readableTime_2

        val alarmTimeMilsec = calender.timeInMillis.toInt()

        calender = Calendar.getInstance()
        calender.set(Calendar.DAY_OF_WEEK, dayOfWeek)
        if (calender.timeInMillis < System.currentTimeMillis()) {
            calender.add(Calendar.DAY_OF_YEAR, 7)
        }

        calender2 = Calendar.getInstance()
        calender2.set(Calendar.DAY_OF_WEEK, dayOfWeek2)
        if (calender2.timeInMillis < System.currentTimeMillis()) {
            calender2.add(Calendar.DAY_OF_YEAR, 7)
        }
        val thuReq: Long = Calendar.getInstance().timeInMillis + 6
        var repReqCode = thuReq.toInt()

        val thuReq2: Long = Calendar.getInstance().timeInMillis + 7
        var repReqCode2 = thuReq2.toInt()
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
//        intent.action = "okay"
//        val time
//        intent.putExtra("time", time)
        intent.flags = Intent.FLAG_ACTIVITY_MULTIPLE_TASK

        val pendingIntent = PendingIntent.getBroadcast(this, repReqCode, intent, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calender.timeInMillis,
            // Interval one day
            24 * 60 * 60 * 1000 * 7,
            pendingIntent
        )
        pi = PendingIntent.getBroadcast(this, repReqCode2, intent, 0)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calender2.timeInMillis,
            // Interval one day
            24 * 60 * 60 * 1000 * 7,
            pi
        )

        alarmDataHelper?.insertMultiAlarmData(
            medicineName,
            totReadabletime,
            alarmTimeMilsec,
            repReqCode,
            repReqCode2,
            dayName,
            autogenerateKey
        )

        Toast.makeText(this, "Alarm set succesfully", Toast.LENGTH_SHORT).show()

    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
        val intent = Intent(this, AlarmActivity2::class.java)
        startActivity(intent)

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