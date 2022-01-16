package com.example.randomuser.AlarmClock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.randomuser.R
import kotlinx.android.synthetic.main.activity_alarm_details.*

class AlarmDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_details)


        val intent = intent
           var rcvTime =  intent.getStringExtra("alarmTime")
           var rcvTime2 =  intent.getStringExtra("alarmTime2")
           var rcvMedicineName =  intent.getStringExtra("alarmName")
           var rcvMedicineName2 =  intent.getStringExtra("alarmName2")
           var rcvDays =  intent.getStringExtra("alarmDays")


        timeView_id.text= rcvTime
        timeView2_id.text= rcvTime2
        MedicineTextView_id.text= rcvMedicineName
        MedicineTextView2_id.text= rcvMedicineName2
        alarmDaysView_id.text= rcvDays


    }
}