package com.emir.notdefteri

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dtbac (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    data class EklenenNot(val id: String?, val veri: String, val top: String)

    companion object {
        private const val DATABASE_NAME = "notlar.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE IF NOT EXISTS notlar " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "baslik TEXT, " +
                "icerik TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS notlarim")
        onCreate(db)
    }

    @SuppressLint("Range")
    fun icerigial(): MutableList<EklenenNot> {
        val mylist = mutableListOf<EklenenNot>()
        val rd = readableDatabase
        val cursor = rd.rawQuery("select * from notlar", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndex("id"))
                val icerik = cursor.getString(cursor.getColumnIndex("baslik"))
                val baslik = cursor.getString(cursor.getColumnIndex("icerik"))
                val eklenenNot = EklenenNot(id, icerik, baslik)
                mylist.add(eklenenNot)
            } while (cursor.moveToNext())
        }
        cursor.close()
        rd.close()
        return mylist
    }

    fun ekle(eklenenNot: EklenenNot) {
        val values = ContentValues()
        values.put("baslik", eklenenNot.top)
        values.put("icerik", eklenenNot.veri)

        writableDatabase.use {wd -> wd.insert("notlar", null, values)}
    }

    fun guncelle(eklenenNot: EklenenNot) {
        val values = ContentValues()
        values.put("baslik", eklenenNot.top)
        values.put("icerik", eklenenNot.veri)

        writableDatabase.use {wd -> wd.update("notlar", values, "id = ?", arrayOf(eklenenNot.id.toString()))}
    }

    fun sil(id: String) {
        writableDatabase.use {wd -> wd.delete("notlar", "id = ?", arrayOf(id))}
    }
}