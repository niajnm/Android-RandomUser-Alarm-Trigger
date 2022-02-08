package Yoga

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.randomuser.R
import kotlinx.android.synthetic.main.activity_yoga_menu.*

class YogaMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yoga_menu)


        beginner_id.setOnClickListener {

            val intent = Intent(this,BeginnerActivity::class.java)
            startActivity(intent)
        }

        yoga_inter.setOnClickListener {

        }
    }
}