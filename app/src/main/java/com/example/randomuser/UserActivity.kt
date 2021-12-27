package com.example.randomuser

import Database.DataModel
import Database.DatabaseHelper
import android.content.ContentValues.TAG
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randomuser.APIClient.ATservice
import com.example.randomuser.Users.ProfileDeatailsActivity
import com.example.randomuser.model.Result
import com.example.randomuser.model.User
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class UserActivity : AppCompatActivity() {
    var customAdapter: CustomAdapter? = null

    var name: String? = null
    var gender: String? = null
    var mail: String? = null
    var databaseHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        setSupportActionBar(usertoolbar_id)
        title = "Users"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)


        databaseHelper = DatabaseHelper(this)
        val sqLiteDatabase = databaseHelper!!.writableDatabase


        usertoolbar_id!!.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })

        getapi()
        customAdapter = CustomAdapter(
            this
        )
        recycler!!.adapter = customAdapter
        recycler!!.layoutManager = LinearLayoutManager(this)

    }

    fun getapi() {
        val reqcall = ATservice().getRandomUser()
        reqcall.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val code = response.code()
                    var list = mutableListOf<Result>()
                    val value = response.body()!!.results as MutableList<Result>
                    list.addAll(value)
                    customAdapter?.setData(list)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun passDetails(
        userName: String?,
        userGender: String?,
        userMail: String?,
        userImgData: String?,
        userlocation: String,
        userphone: String,
        userAge: String
    ) {
        var countid=0
        val cursor = databaseHelper.DisplayData()
        val Rdata: ArrayList<DataModel> = loadData(cursor)
        Log.d(TAG, "ALLDATA" + Rdata)

        if (Rdata.isEmpty()) {

            databaseHelper.insertCartData(userName,userGender,userlocation,userAge,userphone,userMail,userImgData)

        }
        else {

                if (Rdata.size < 5) {
                    Toast.makeText(this, "update", Toast.LENGTH_SHORT).show()
                    // databaseHelper!!.updateCartData(title,cartID,price,type,count.toString())

                    databaseHelper.insertCartData(userName,userGender,userlocation,userAge,userphone,userMail,userImgData)


                }
                else {

                    val db: SQLiteDatabase = databaseHelper.getWritableDatabase()
                        countid= countid +1
                  //  db.execSQL("DELETE "TOP"(1) FROM User_History")
                    db.execSQL("DELETE FROM User_History WHERE id_=$countid")
                    databaseHelper.insertCartData(userName,userGender,userlocation,userAge,userphone,userMail,userImgData)
                }

        }



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
    fun loadData(cursor: Cursor): ArrayList<DataModel> {

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


}