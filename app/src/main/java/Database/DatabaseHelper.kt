package Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DatabaseHelper(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION_NUM) {

    companion object {
        private const val DATABASE_NAME = "Users_DB"
        private const val TABLE_NAME = "User_History"
        private const val NAME = "Name"
        private const val AGE = "Age"

        private const val COUNTRY = "Country"
        private const val GENDER = "Gender"
        private const val IMG = "imageUrl"
        private const val PHONE = "Phone"
        private const val MAIL = "Mail"
        private const val ID = "id_"
        private const val DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME
        private const val VERSION_NUM = 3
        private const val CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER  PRIMARY KEY AUTOINCREMENT," + NAME + " VARCHAR(50) UNIQUE," + AGE + " INT," + GENDER + " VARCHAR(5), "+ COUNTRY + " VARCHAR(20)," + PHONE + " VARCHAR(15), " + MAIL + " VARCHAR(50), $IMG VARCHAR(500))"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL(CREATE_TABLE)

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


   fun setHistory(name: String?, gender: String?, mail: String?) {
       val db: SQLiteDatabase = getWritableDatabase()




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


       //db.execSQL("Insert into $TABLE_NAME ( $name, $gender,mail);")






}