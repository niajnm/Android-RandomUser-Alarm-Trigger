package com.example.randomuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.randomuser.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_deatails.*

class ProfileDeatailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_profile_deatails)

        val intent = intent
        var name = intent.getStringExtra("dataName")
        var gender = intent.getStringExtra("dataGender")
        var mail = intent.getStringExtra("dataMail")
        var img1 = intent.getStringExtra("dataImg1")
        var locationCountry = intent.getStringExtra("datalocation")
        var phone = intent.getStringExtra("dataPhone")
        var age = intent.getStringExtra("dataAge")

        profileName_id.text = name
        textView_mail_id.text = mail
        TextView_location_id.text = locationCountry
        textVeiw_phone_id.text = phone
        textViewAge_id.text = age
        Picasso.get().load(img1).into(circleImg_id)

        if (gender == "male") {
            Gender_img_id.setImageResource(R.drawable.male)

        } else {
            Gender_img_id.setImageResource(R.drawable.female)
        }
        profile_backbutton_id.setOnClickListener{

            onBackPressed()

        }


    }
}