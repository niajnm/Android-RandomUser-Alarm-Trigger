package Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.util.*

class DatabaseHelper(var context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION_NUM) {

    companion object {
        private const val DATABASE_NAME = "Users_DB"
        private const val TABLE_NAME = "User_History"
        private const val ALARM_TABLE_NAME = "Alarm_history"
        private const val MULTI_ALARM_TABLE_NAME = "Multiple_Alarm"
        private const val SINGLE_ALARM_TABLE_NAME = "Single_Alarm"
        private const val BLOODP_HISTORY_TABLE_NAME = "bp_history"

        private const val COUNTRY = "Country"
        private const val AGE = "Age"
        private const val NAME = "Name"

        private const val GENDER = "Gender"
        private const val IMG = "imageUrl"
        private const val PHONE = "Phone"
        private const val MAIL = "Mail"
        private const val ID = "id_"

        private const val TITLE = "med_name"
        private const val TITLE2 = "med_name2"
        private const val CTIME = "calender_time"
        private const val CTIME2 = "calender_time2"
        private const val REQC = "request_code_1"
        private const val REQC2 = "request_cod_2"
        private const val FLAG = "flag"
        private const val WEEKDAYS = "week_days"
        private const val Fkey = "frn_Key"
        private const val Time = "alarm_time"
        private const val Time2 = "alarm_time2"


        private const val PNAME = "Patient_name"
        private const val BPDATE = "date"
        private const val BPTIME = "time"
        private const val CALMILISEC = "cal_milisec"
        private const val SYS = "Systolic"
        private const val DIAS = "Diastolic"
        private const val PULSE = "pulse"
        private const val RESULT = "result"
        private const val MAP = "map"
        private const val COLORCODE = "color_cd"
        private const val CHARTVAL = "chart_val"
        private const val POSITION = "position"
        private const val EXTRMITY = "extrimity"

        private const val DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME
        private const val VERSION_NUM = 1
        private const val CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER  PRIMARY KEY AUTOINCREMENT," + NAME + " VARCHAR(50) UNIQUE," + AGE + " INT," + GENDER + " VARCHAR(5), " + COUNTRY + " VARCHAR(20)," + PHONE + " VARCHAR(15), " + MAIL + " VARCHAR(50), $IMG VARCHAR(500))"
        private const val ALARM_TABLE =
            "CREATE TABLE $ALARM_TABLE_NAME($ID INTEGER  PRIMARY KEY AUTOINCREMENT,$TITLE VARCHAR(50),$TITLE2 VARCHAR(50),$Time VARCHAR(20),$Time2 VARCHAR(20),$CTIME INT(100),$REQC INT(200),$REQC2 INT(200), $FLAG VARCHAR(50), $WEEKDAYS VARCHAR(500),$Fkey Int(200))"

        private const val SINGLE_ALARM_TABLE =
            "CREATE TABLE $SINGLE_ALARM_TABLE_NAME($ID INTEGER  PRIMARY KEY AUTOINCREMENT,$TITLE VARCHAR(50),$TITLE2 VARCHAR(50),$Time VARCHAR(20),$CTIME INT(100),$REQC INT(200), $FLAG VARCHAR(50), $WEEKDAYS VARCHAR(500),$Fkey Int(200))"

        private const val MULTI_ALARM_TABLE =
            "CREATE TABLE $MULTI_ALARM_TABLE_NAME($ID INTEGER  PRIMARY KEY AUTOINCREMENT,$TITLE VARCHAR(50),$Time VARCHAR(20),$CTIME INT(500),$CTIME2 INT(500),$REQC INT(200),$REQC2 INT(200),$FLAG VARCHAR(50), $WEEKDAYS VARCHAR(500),$Fkey Int(200))"

        private const val BLOOD_TABLE =
            "CREATE TABLE $BLOODP_HISTORY_TABLE_NAME ($ID INTEGER  PRIMARY KEY AUTOINCREMENT,$PNAME VARCHAR(50),$BPDATE DATE(20),$BPTIME VARCHAR(20),$SYS VARCHAR(10),$DIAS VARCHAR(10),$PULSE INT,$RESULT VARCHAR(20),$MAP INT,$COLORCODE INT,$CHARTVAL INT,$POSITION VARCHAR(20),$EXTRMITY VARCHAR(20),$CALMILISEC INT(500))"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL(CREATE_TABLE)
            db?.execSQL(ALARM_TABLE)
            db?.execSQL(SINGLE_ALARM_TABLE)
            db?.execSQL(MULTI_ALARM_TABLE)
            db?.execSQL(BLOOD_TABLE)

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

    fun DisplayBPData(): Cursor {
        val sqLiteDatabase = this.writableDatabase
        return sqLiteDatabase.rawQuery(
            "SELECT * FROM $BLOODP_HISTORY_TABLE_NAME ORDER BY id_ DESC",
            null
        )
    }

    fun DisplayChartData(chartKey: Int): Cursor {
        val sqLiteDatabase = this.writableDatabase
        return sqLiteDatabase.rawQuery(
            "SELECT * FROM $BLOODP_HISTORY_TABLE_NAME where color_cd =$chartKey ",
            null
        )
    }

    fun searchChartData(dateKey1: Long, dateKey2: Long): Cursor {
        val sqLiteDatabase = this.writableDatabase
        //  return sqLiteDatabase.rawQuery("SELECT * FROM $BLOODP_HISTORY_TABLE_NAME where date BETWEEN strftime('%y-%m-%d', '$dateKey1') AND strftime('%y-%m-%d', '$dateKey2')", null)
        return sqLiteDatabase.rawQuery("SELECT * FROM $BLOODP_HISTORY_TABLE_NAME where $CALMILISEC BETWEEN $dateKey1 AND $dateKey2",null)
    }


    fun displayAlarmData(): Cursor {
        val sqLiteDatabase = this.writableDatabase
        return sqLiteDatabase.rawQuery("SELECT * FROM $ALARM_TABLE_NAME", null)
    }

    fun loadDataASC(): Cursor {
        val sqLiteDatabase = this.writableDatabase
        return sqLiteDatabase.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY id_ ASC LIMIT 1", null)
    }

    fun searchAnalysis(d1: Long): Cursor {

        val sqLiteDatabase = this.writableDatabase
        return sqLiteDatabase.rawQuery(
            "SELECT * FROM $BLOODP_HISTORY_TABLE_NAME ORDER BY id_ ASC LIMIT $d1",
            null
        )

    }

    fun loadAlarmDatadDelete(magicKey: Int): Cursor {
        val sqLiteDatabase = this.writableDatabase
        return sqLiteDatabase.rawQuery(
            "SELECT * FROM $MULTI_ALARM_TABLE_NAME WHERE frn_key= $magicKey",
            null
        )
    }

    fun loadAlarmDataOn(magicKey: Int): Cursor {
        val sqLiteDatabase = this.writableDatabase
        return sqLiteDatabase.rawQuery(
            "SELECT * FROM $MULTI_ALARM_TABLE_NAME WHERE frn_key= $magicKey",
            null
        )
    }

//
//    fun DisplayChartData(magicKey: Int): Cursor {
//        val sqLiteDatabase = this.writableDatabase
//        return sqLiteDatabase.rawQuery(
//            "BLOODP_HISTORY$BLOODP_HISTORY_TABLE_NAME WHERE color_cd= $magicKey",
//            null
//        )
//    }

    fun dataDelete(numId: String?) {
        val db: SQLiteDatabase = getWritableDatabase()
        db.execSQL("DELETE FROM User_History WHERE id_=$numId")
    }

    fun alarmUpdate(cTime: Long?, newtime: Long) {
        val db: SQLiteDatabase = writableDatabase
        db.execSQL("UPDATE $MULTI_ALARM_TABLE_NAME SET calender_time =$newtime WHERE calender_time=$cTime ")
    }

    fun alarmUpdateTwice(cTime2: Long?, newtime2: Long) {
        val db: SQLiteDatabase = writableDatabase
        db.execSQL("UPDATE $MULTI_ALARM_TABLE_NAME SET calender_time2 =$newtime2 WHERE calender_time2=$cTime2 ")
    }

    fun deleteAlarmData(numId: Int) {
        val db: SQLiteDatabase = writableDatabase
        db.execSQL("DELETE FROM $ALARM_TABLE_NAME WHERE frn_Key=$numId")
        db.execSQL("DELETE FROM $MULTI_ALARM_TABLE_NAME WHERE frn_Key=$numId")
    }
    fun deleteBpData(id: Int) {
        val db: SQLiteDatabase = writableDatabase
        db.execSQL("DELETE FROM $BLOODP_HISTORY_TABLE_NAME WHERE id_=$id")
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

    fun bpInsertData(
        name: String?,
        date: String,
        time: String?,
        sys: String,
        dias: String,
        pulse: Int,
        result: String?,
        map: Int?,
        color: Int?,
        chart: Int?,
        position: String?,
        extrimity: String?,
        cal_milisec: Long
    ) {
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(PNAME, name)
        contentValues.put(BPDATE, date)
        contentValues.put(BPTIME, time)
        contentValues.put(SYS, sys)
        contentValues.put(DIAS, dias)
        contentValues.put(PULSE, pulse)
        contentValues.put(RESULT, result)
        contentValues.put(MAP, map)
        contentValues.put(COLORCODE, color)
        contentValues.put(CHARTVAL, chart)
        contentValues.put(POSITION, position)
        contentValues.put(EXTRMITY, extrimity)
        contentValues.put(CALMILISEC, cal_milisec)

        sqLiteDatabase.insert(BLOODP_HISTORY_TABLE_NAME, null, contentValues)
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
        medicineName2: String?,
        readableTime: String?,
        readableTime2: String?,
        alarmTimeMilsec: Long,
        repReqCode1: Int,
        repReqCode2: Int,
        status: String,
        foreignKey: Int
    ) {
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TITLE, medicineName)
        contentValues.put(TITLE2, medicineName2)
        contentValues.put(Time, readableTime)
        contentValues.put(Time2, readableTime2)
        contentValues.put(CTIME, alarmTimeMilsec)
        contentValues.put(REQC, repReqCode1)
        contentValues.put(REQC2, repReqCode2)
        contentValues.put(WEEKDAYS, status)
        contentValues.put(Fkey, foreignKey)
        sqLiteDatabase.insert(ALARM_TABLE_NAME, null, contentValues)
    }

    fun insertMultiAlarmData(
        medicineName: String?,
        readableTime: String?,
        alarmTimeMilsec: Long,
        alarmTimeMilsec2: Long,
        repReqCode1: Int,
        repReqCode2: Int,
        status: String,
        foreignKey: Int
    ) {
        val sqLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TITLE, medicineName)
        contentValues.put(Time, readableTime)
        contentValues.put(CTIME, alarmTimeMilsec)
        contentValues.put(CTIME2, alarmTimeMilsec2)
        contentValues.put(REQC, repReqCode1)
        contentValues.put(REQC2, repReqCode2)
        contentValues.put(WEEKDAYS, status)
        contentValues.put(Fkey, foreignKey)
        sqLiteDatabase.insert(MULTI_ALARM_TABLE_NAME, null, contentValues)
    }

    fun loadAlarmData(cursor: Cursor): ArrayList<DataModel> {

        val dataList: ArrayList<DataModel> = ArrayList<DataModel>()
        if (cursor.count == 0) {

        } else {
            while (cursor.moveToNext()) {
                val alarmData = DataModel()
                alarmData.medicineName = cursor.getString(1)
                alarmData.medicineName2 = cursor.getString(2)
                alarmData.alarmTime = cursor.getString(3)
                alarmData.alarmTime2 = cursor.getString(4)
                alarmData.alarmTMilsec = cursor.getLong(5)
                alarmData.alarmDays = cursor.getString(9)
                alarmData.alarmMagicKey = cursor.getInt(10)
                dataList.add(alarmData)
            }
        }
        return dataList
    }

    fun loadDeleteAlarmData(cursor: Cursor): ArrayList<DataModel> {

        val dataList: ArrayList<DataModel> = ArrayList<DataModel>()
        if (cursor.count == 0) {

        } else {
            while (cursor.moveToNext()) {
                val alarmData = DataModel()

                alarmData.requestCode = cursor.getInt(5)
                alarmData.requestCode2 = cursor.getInt(6)

                dataList.add(alarmData)
            }
        }
        return dataList
    }

    fun OnAlarmData(cursor: Cursor): ArrayList<DataModel> {

        val dataList: ArrayList<DataModel> = ArrayList<DataModel>()
        if (cursor.count == 0) {

        } else {
            while (cursor.moveToNext()) {
                val alarmData = DataModel()

                alarmData.alarmTMilsec = cursor.getLong(3)
                alarmData.alarmTMilsec2 = cursor.getLong(4)
                alarmData.requestCode = cursor.getInt(5)
                alarmData.requestCode2 = cursor.getInt(6)

                dataList.add(alarmData)
            }
        }
        return dataList
    }

    fun searchChartDataload(cursor: Cursor): ArrayList<DataModel> {

        val dataList: ArrayList<DataModel> = ArrayList<DataModel>()
        if (cursor.count == 0) {

        } else {
            while (cursor.moveToNext()) {
                val bpData = DataModel()
                bpData.bpId = cursor.getInt(0)
                bpData.bpName = cursor.getString(1)
                bpData.bpDate = cursor.getString(2)
                bpData.bpTime = cursor.getString(3)
                bpData.bpSys = cursor.getString(4)
                bpData.bpDias = cursor.getString(5)
                bpData.bpPulse = cursor.getInt(6)
                bpData.bpResult = cursor.getString(7)
                bpData.bpMap = cursor.getInt(8)
                bpData.bpColor = cursor.getInt(9)
                bpData.bpChart = cursor.getInt(10)
                bpData.bpPosition = cursor.getString(11)
                bpData.bpExtrimity = cursor.getString(12)
                dataList.add(bpData)
            }
        }
        return dataList
    }

    fun loadBpData(cursor: Cursor): ArrayList<DataModel> {

        val dataList: ArrayList<DataModel> = ArrayList<DataModel>()
        if (cursor.count == 0) {

        } else {
            while (cursor.moveToNext()) {
                val bpData = DataModel()
                bpData.bpId = cursor.getInt(0)
                bpData.bpName = cursor.getString(1)
                bpData.bpDate = cursor.getString(2)
                bpData.bpTime = cursor.getString(3)
                bpData.bpSys = cursor.getString(4)
                bpData.bpDias = cursor.getString(5)
                bpData.bpPulse = cursor.getInt(6)
                bpData.bpResult = cursor.getString(7)
                bpData.bpMap = cursor.getInt(8)
                bpData.bpColor = cursor.getInt(9)
                bpData.bpChart = cursor.getInt(10)
                bpData.bpPosition = cursor.getString(11)
                bpData.bpExtrimity = cursor.getString(12)
                dataList.add(bpData)
            }
        }
        return dataList

    }




}