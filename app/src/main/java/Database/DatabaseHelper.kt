package Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.util.ArrayList

class DatabaseHelper(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION_NUM) {

    companion object {
        private const val DATABASE_NAME = "Users_DB"
        private const val TABLE_NAME = "User_History"
        private const val ALARM_TABLE_NAME = "Alarm_history"

        private const val COUNTRY = "Country"
        private const val AGE = "Age"
        private const val NAME = "Name"

        private const val GENDER = "Gender"
        private const val IMG = "imageUrl"
        private const val PHONE = "Phone"
        private const val MAIL = "Mail"
        private const val ID = "id_"

        private const val TITLE = "med_name"
        private const val CTIME = "c_time"
        private const val REQC = "request_code"
        private const val FLAG = "flag"
        private const val WEEKDAYS = "week_days"
        private const val Time = "alarm_time"

        private const val DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME
        private const val VERSION_NUM = 3
        private const val CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER  PRIMARY KEY AUTOINCREMENT," + NAME + " VARCHAR(50) UNIQUE," + AGE + " INT," + GENDER + " VARCHAR(5), "+ COUNTRY + " VARCHAR(20)," + PHONE + " VARCHAR(15), " + MAIL + " VARCHAR(50), $IMG VARCHAR(500))"
        private const val ALARM_TABLE =
            "CREATE TABLE $ALARM_TABLE_NAME($ID INTEGER  PRIMARY KEY AUTOINCREMENT,$TITLE VARCHAR(50) UNIQUE,$Time VARCHAR(20),$CTIME INT(100),$REQC INT(200), $FLAG VARCHAR(50), $WEEKDAYS VARCHAR(500))"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL(CREATE_TABLE)
            db?.execSQL(ALARM_TABLE)

            Toast.makeText(context, "oncreate", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "EXCEPTION$e", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun DisplayData(): Cursor {
        val sqLiteDatabase = this.writableDatabase
        return sqLiteDatabase.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY id_ DESC", null)
    }

    fun displayAlarmData(): Cursor {
        val sqLiteDatabase = this.writableDatabase
        return sqLiteDatabase.rawQuery("SELECT * FROM $ALARM_TABLE_NAME", null)
    }

    fun loadDataASC(): Cursor {
        val sqLiteDatabase = this.writableDatabase
        return sqLiteDatabase.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY id_ ASC LIMIT 1", null)
    }


   fun dataDelete(numId: String?) {
       val db: SQLiteDatabase = getWritableDatabase()
       db.execSQL("DELETE FROM User_History WHERE id_=$numId")
   }
       fun insertCartData(
           name: String?,
           gender: String?,
           country: String?,
           userAge: String,
           userphone: String,
           userMail: String?,
           userImgData: String?
       ) {
           val sqLiteDatabase = this.writableDatabase
           val contentValues = ContentValues()
           contentValues.put(NAME, name)
           contentValues.put(GENDER, gender)
           contentValues.put(COUNTRY, country)
           contentValues.put(AGE, userAge)
           contentValues.put(PHONE, userphone)
           contentValues.put(MAIL, userMail)
           contentValues.put(IMG, userImgData)

            sqLiteDatabase.insert(TABLE_NAME, null, contentValues)
       }

    fun loadData(cursor: Cursor): ArrayList<DataModel> {

        val dataList: ArrayList<DataModel> = ArrayList<DataModel>()
        if (cursor.count == 0) {

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

    fun insertAlarmData(
        medicineName: String?,
        readableTime: String?,
        alarmTimeMilsec: Int,
        repReqCode: Int,
        status: String
    ) {
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TITLE, medicineName)
        contentValues.put(Time, readableTime)
        contentValues.put(CTIME, alarmTimeMilsec)
        contentValues.put(REQC, repReqCode)
        contentValues.put(WEEKDAYS, status)
        sqLiteDatabase.insert(ALARM_TABLE_NAME, null, contentValues)
    }

    fun loadAlarmData(cursor: Cursor): ArrayList<DataModel> {

        val dataList: ArrayList<DataModel> = ArrayList<DataModel>()
        if (cursor.count == 0) {

        } else {
            while (cursor.moveToNext()) {
                val alarmData = DataModel()
                alarmData.medicineName = cursor.getString(1)
                alarmData.alarmTime = cursor.getString(2)
                alarmData.alarmTMilsec = cursor.getString(3)
                alarmData.alarmDays = cursor.getString(6)

                dataList.add(alarmData)
            }
        }
        return dataList
    }




}