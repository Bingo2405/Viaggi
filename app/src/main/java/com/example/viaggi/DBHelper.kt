package com.example.viaggi



import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract.CommonDataKinds.Email


class DBHelper(context: Context) : SQLiteOpenHelper(context, dbName, null, dbVersion) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE users (email TEXT primary key, password TEXT )")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Implementa qui la logica di aggiornamento del database
        db.execSQL("drop Table if exists users")
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Implementa qui la logica di downgrade del database
    }

    fun insertData(email: String, password: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("email", email)
        contentValues.put("password", password)

        val result = db.insert("users", null, contentValues)
        return result != -1L //restituisci true se il valore non è uguale a -1L, altrimenti restituisci false". Questa espressione controlla se il valore è diverso da -1L.
    }

    fun checkmail(email: String,password: String): Boolean {
        val db =this.writableDatabase
        val query = "select * from users where email = '$email' and password = '$password'"
        val cursor= db.rawQuery(query,null)
        if(cursor.count <=0){
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }



    companion object {
        const val dbName = "dbChiovaros"
        const val dbVersion = 1
    }
}


