package com.example.randomuser

import BloodPressure.PressureCalculateActivity
import Database.DatabaseHelper
import News.NewsActivity
import Yoga.PlayerActivity
import Yoga.YogaMenuActivity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.CharacterPickerDialog
import android.widget.Toast
import com.example.randomuser.AlarmClock.AlarmActivity2
import com.example.randomuser.AlarmClock.AlarmReceiver
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

        newscard_id.setOnClickListener{
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }

        cardYoga_id.setOnClickListener{
            val intent = Intent(this, YogaMenuActivity::class.java)
            startActivity(intent)
        }
        cardBlood_id.setOnClickListener{
            val intent = Intent(this, PressureCalculateActivity::class.java)
            startActivity(intent)
        }

        card_test.setOnClickListener {


        }
        val  i = Intent(this, AlarmReceiver::class.java)
        this.startService(i)
    }

    override fun onBackPressed() {
       var builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder!!.setTitle("Exit !")
        builder!!.setMessage("Do you want to exit ?")
        builder!!.setIcon(R.drawable.ic_baseline_cancel_24)
        builder!!.setPositiveButton("Yes", { dialog, which -> finish() })
        builder!!.setNegativeButton("No") { dialog, which ->
            Toast.makeText(
                this,
                "Back to main menu !",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder!!.setNeutralButton("Minimize") { dialog, which -> onBackPressed() }
        val alertDialog: androidx.appcompat.app.AlertDialog = builder!!.create()
        alertDialog.show()
    }
}