package com.example.studentareaproject


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "MyBD"
val TABLE_NAME = "Users"
val COL_NAME = "username"
val COL_PASS = "password"
val COL_ID = "id"

class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        // val dropTable = "DELETE from $TABLE_NAME";
        val createTable = "CREATE TABLE " + TABLE_NAME +" (" +
                COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME +" VARCHAR(256)," +
                COL_PASS +" VARCHAR(256))";
        // db?.execSQL(dropTable)
        db?.execSQL(createTable)
    }


    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(user : User){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, user.username)
        cv.put(COL_PASS, user.password)
        var result = db.insert(TABLE_NAME, null,cv)
        if(result == (-1).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }
    /// O read data agora deve só fazer a pesquisa de um usuário e não de todos. O problema do login é pq ele não chega até o final do loop.
    fun readData() : MutableList<User>{
        var list : MutableList<User> = ArrayList()

        val db = this.readableDatabase
        val query = "select * from " + TABLE_NAME
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var user = User()
                user.id = result.getString(0).toInt()
                user.username = result.getString(1).toString()
                user.password = result.getString(2).toString()
                list.add(user)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

//    fun getUser(user : User){
//        val cv = ContentValues()
//        cv.put(COL_NAME, user.username)
//        userAuthData = 0
//        val db = this.readableDatabase
//        val query = "select * from " + TABLE_NAME + " where username == " + ""
//        val result = db.rawQuery(query, null)
//    }


}