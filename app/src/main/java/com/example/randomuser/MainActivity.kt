package com.example.randomuser

import Database.DatabaseHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.randomuser.Users.ProfileDeatailsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        cardUser_id.setOnClickListener({
            val intent = Intent(this,UserActivity::class.java)
            startActivity(intent)
        })

        cardView_profile_id.setOnClickListener({
            val intent = Intent(this,ProfileDeatailsActivity::class.java)
            startActivity(intent)
        })

        cardHistory_id.setOnClickListener({
            val intent = Intent(this,HistoryActivity::class.java)
            startActivity(intent)
        })


    }
}