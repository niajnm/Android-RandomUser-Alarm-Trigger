package com.example.randomuser

import Database.DataModel
import Database.DatabaseHelper
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.randomuser.APIClient.ATservice
import com.example.randomuser.model.Result
import com.example.randomuser.model.User
import kotlinx.android.synthetic.main.activity_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class UserActivity : AppCompatActivity() {
    var customAdapter: CustomAdapter? = null
    var name: String? = null
    var databaseHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        setSupportActionBar(usertoolbar_id)
        title = "Users"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        databaseHelper = DatabaseHelper(this)
        val sqLiteDatabase = databaseHelper!!.writableDatabase
        getapi()

        customAdapter = CustomAdapter(this)
        recycler!!.adapter = customAdapter
        recycler!!.layoutManager = LinearLayoutManager(this)

        usertoolbar_id!!.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })
    }

    private fun getapi() {
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
        userName: String,
        userGender: String,
        userMail: String,
        userImgData: String?,
        userlocation: String,
        userphone: String,
        userAge: String
    ) {

        val cursor = databaseHelper.DisplayData()
        val cursorAsc = databaseHelper.loadDataASC()
        val databaseListDsc: ArrayList<DataModel> = databaseHelper.loadData(cursor)
        val dataList: ArrayList<DataModel> = databaseHelper.loadData(cursorAsc)
        val db: SQLiteDatabase = databaseHelper.getWritableDatabase()

        if (databaseListDsc.isEmpty()) {
            databaseHelper.insertCartData(
                userName,
                userGender,
                userlocation,
                userAge,
                userphone,
                userMail,
                userImgData
            )
        } else {

            if (databaseListDsc.isNotEmpty()) {
                for (i in databaseListDsc.indices) {
                    if (userName == databaseListDsc[i].historyName) {
                        var nameId = databaseListDsc[i].historyID
                        databaseHelper.dataDelete(nameId)
                        databaseHelper.insertCartData(
                            userName,
                            userGender,
                            userlocation,
                            userAge,
                            userphone,
                            userMail,
                            userImgData
                        )
                        break
                    }
                }
            }

            if (databaseListDsc.size < 10) {
                Toast.makeText(this, "update", Toast.LENGTH_SHORT).show()

                databaseHelper.insertCartData(
                    userName,
                    userGender,
                    userlocation,
                    userAge,
                    userphone,
                    userMail,
                    userImgData
                )

            } else if (databaseListDsc.size == 10) {

                val test = dataList[0].historyID
                databaseHelper.dataDelete(test)
                databaseHelper.insertCartData(
                    userName,
                    userGender,
                    userlocation,
                    userAge,
                    userphone,
                    userMail,
                    userImgData
                )
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
}