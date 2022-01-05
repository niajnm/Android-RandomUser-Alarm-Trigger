package com.example.randomuser

import Database.DatabaseHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.randomuser.AlarmClock.AlarmActivity2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var databaseHelper : DatabaseHelper?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)
        val sqLiteDatabase = databaseHelper!!.writableDatabase

        cardUser_id.setOnClickListener{
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        cardView_profile_id.setOnClickListener{
            val intent = Intent(this, AlarmActivity2::class.java)
            startActivity(intent)
        }

        cardHistory_id.setOnClickListener{
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        cardAlarm_id.setOnClickListener{
            val intent = Intent(this, AlarmActivity2::class.java)
            startActivity(intent)
        }


    }
}