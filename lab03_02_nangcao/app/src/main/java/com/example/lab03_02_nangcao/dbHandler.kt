package com.example.lab03_02_nangcao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class dbHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT," +
                CLASS_COL + " TEXT" + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    companion object {
        private val DATABASE_NAME = "STUDENT_INFO"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "student_info"
        val ID_COL = "id"
        val NAME_COl = "name"
        val CLASS_COL = "class"
    }

    fun addStudent(student: Student) {
        val db = this.writableDatabase
        val myValues = ContentValues()
        myValues.put(com.example.lab03_02_nangcao.dbHandler.ID_COL, student.mssv)
        myValues.put(com.example.lab03_02_nangcao.dbHandler.NAME_COl, student.name)
        myValues.put(com.example.lab03_02_nangcao.dbHandler.CLASS_COL, student.classroom)
        db.insert(com.example.lab03_02_nangcao.dbHandler.TABLE_NAME, null, myValues)
        db.close()
    }

    fun getStudent(id: String): Student {
        val db = this.readableDatabase
        val c = db.query(
            com.example.lab03_02_nangcao.dbHandler.TABLE_NAME,
            null,
            "id = ?",
            arrayOf<String>(id),
            null,
            null,
            null
        )
        c?.moveToFirst()
        val student = Student(c!!.getString(0), c!!.getString(1), c!!.getString(2))
        db.close()
        return student
    }

    fun getAllStudent(): List<Student> {
        val db = this.readableDatabase
        val ls: MutableList<Student> = ArrayList()
        val c = db.query(
            com.example.lab03_02_nangcao.dbHandler.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
        c.moveToFirst()
        while (c.isAfterLast == false) {
            val student = Student(c.getString(0), c.getString(1), c.getString(2))
            ls.add(student)
            c.moveToNext()
        }
        db.close()
        return ls
    }

    fun updateStudent(student: Student) {
        val db = this.writableDatabase
        val value = ContentValues()
        value.put(com.example.lab03_02_nangcao.dbHandler.NAME_COl, student.name)
        value.put(com.example.lab03_02_nangcao.dbHandler.CLASS_COL, student.classroom)
        db.update(
            com.example.lab03_02_nangcao.dbHandler.TABLE_NAME,
            value,
            "id = ?",
            arrayOf<String>(student.mssv)
        )
        db.close()
    }

    fun deleteStudent(id: String) {
        val db = writableDatabase
        db.delete(com.example.lab03_02_nangcao.dbHandler.TABLE_NAME, "id = ?", arrayOf<String>(id))
        db.close()
    }

    fun deleteAllStudent() {
        val db = writableDatabase
        db.delete(com.example.lab03_02_nangcao.dbHandler.TABLE_NAME, null, null)
        db.close()
    }
}