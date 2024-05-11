package com.example.lab03_part2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity() {
    private val DB_PATH_SUFFIX = "/databases/"
    private var database: SQLiteDatabase? = null
    private val DATABASE_NAME = "qlsv.db"
    private lateinit var lv: ListView
    private lateinit var mylist: ArrayList<String>
    private lateinit var myadapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        processCopy()
        database = openOrCreateDatabase("qlsv.db", MODE_PRIVATE, null)
        lv = findViewById(R.id.lv_content)
        mylist = ArrayList()
        myadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mylist)
        lv.adapter = myadapter
        val c: Cursor = database!!.query("sinhvien", null, null, null, null, null, null)
        c.moveToFirst()
        var data = ""
        while (!c.isAfterLast) {
            data = "${c.getString(0)}-${c.getString(1)}-${c.getString(2)}"
            mylist.add(data)
            c.moveToNext()
        }
        c.close()
        myadapter.notifyDataSetChanged()
    }
    private fun processCopy() {
        val dbFile = getDatabasePath(DATABASE_NAME)
        if (!dbFile.exists()) {
            try {
                copyDataBaseFromAsset()
                Toast.makeText(this, "Copying success from Assets folder", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun getDatabasePath(): String {
        return "${applicationInfo.dataDir}$DB_PATH_SUFFIX$DATABASE_NAME"
    }

    @Throws(IOException::class)
    private fun copyDataBaseFromAsset() {
        val myInput: InputStream = assets.open(DATABASE_NAME)
        val outFileName = getDatabasePath()
        val f = File(applicationInfo.dataDir + DB_PATH_SUFFIX)
        if (!f.exists()) f.mkdir()
        val myOutput: OutputStream = FileOutputStream(outFileName)
        val buffer = ByteArray(myInput.available())
        myInput.read(buffer)
        myOutput.write(buffer)
        myOutput.flush()
        myOutput.close()
        myInput.close()
    }
}