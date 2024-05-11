package com.example.lab03

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var dbAdapter: DbAdapter
    private lateinit var cursor: Cursor
    private lateinit var users: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dbAdapter = DbAdapter(this)
        dbAdapter.open()
        dbAdapter.deleteAllUsers()
        users = mutableListOf()
        for (i in 0 until 10) {
            dbAdapter.createUser("Nguyễn Văn An $i")
        }
        getData()
        showData()

    }
    @SuppressLint("Range")
    private fun getData() {
        cursor = dbAdapter.getAllUsers()
        while (cursor.moveToNext()) {
            users.add(cursor.getString(cursor.getColumnIndex(DbAdapter.KEY_NAME)))
        }
    }

    private fun showData() {
        val lvUser = findViewById<ListView>(R.id.lv_user)
        val userAdapter = ArrayAdapter(this, R.layout.item_user, users)
        lvUser.adapter = userAdapter
    }
}