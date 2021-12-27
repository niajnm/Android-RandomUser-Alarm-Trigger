package com.example.randomuser

import Database.DataModel
import Database.DatabaseHelper
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randomuser.Users.ProfileDeatailsActivity
import kotlinx.android.synthetic.main.activity_history.*
import java.util.ArrayList

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        cartdisplay()
    }


    fun cartdisplay() {
        var databaseHelper = DatabaseHelper(this)
        val cursor = databaseHelper!!.DisplayData()
        val Rdata = loadDataCart(cursor)
        val historyAdapter = HistoryAdapter(this, Rdata)
        recycler_history_id.adapter = historyAdapter
        recycler_history_id.layoutManager = LinearLayoutManager(this)

    }



    fun loadDataCart(cursor: Cursor): ArrayList<DataModel> {

        val dataList: ArrayList<DataModel> = ArrayList<DataModel>()
        if (cursor.count == 0) {
            Toast.makeText(this, "No data in database", Toast.LENGTH_LONG).show()
        } else {
            while (cursor.moveToNext()) {
                val dataResponse = DataModel()
                dataResponse.historyID = cursor.getString(0)
                dataResponse.historyName = cursor.getString(1)
                dataResponse.historyAge = cursor.getString(2)
                dataResponse.historyGender = cursor.getString(3)
                dataResponse.historyCountry = cursor.getString(4)
                dataResponse.historyPhone = cursor.getString(5)
                dataResponse.historyMail = cursor.getString(6)
                dataResponse.historyImg = cursor.getString(7)
                dataList.add(dataResponse)
            }
        }
        return dataList
    }

    fun passHistoryDetails(userName: String?, userGender: String?, userMail: String?, userImgData: String?, userlocation: String?, userphone: String?, userAge: String?) {
        val intent = Intent(this, ProfileDeatailsActivity::class.java)
        intent.putExtra("dataName", userName)
        intent.putExtra("dataGender", userGender)
        intent.putExtra("dataMail", userMail)
        intent.putExtra("dataImg1", userImgData)
        intent.putExtra("datalocation", userlocation)
        intent.putExtra("dataPhone", userphone)
        intent.putExtra("dataAge", userAge)
        startActivity(intent)
    }


}