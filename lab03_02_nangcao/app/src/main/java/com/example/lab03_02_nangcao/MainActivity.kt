package com.example.lab03_02_nangcao

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var student: MutableList<Student>
    private lateinit var recyclerStudent: RecyclerView
    private lateinit var studentAdapter: Student_adapter
    private lateinit var btn_insert: Button
    private lateinit var btn_del: Button
    private lateinit var btn_update: Button
    private lateinit var btn_query: Button
    private lateinit var input_mssv: EditText
    private lateinit var input_name: EditText
    private lateinit var input_class: EditText
    private lateinit var db: dbHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = dbHandler(this)
        db.deleteAllStudent()
        btn_insert = findViewById(R.id.button_insert)
        btn_del = findViewById(R.id.button_del)
        btn_update = findViewById(R.id.button_update)
        btn_query = findViewById(R.id.button_query)
        input_mssv = findViewById(R.id.input_mssv)
        input_name = findViewById(R.id.input_name)
        input_class = findViewById(R.id.input_classroom)
        input_mssv.setText("")
        input_name.setText("")
        input_class.setText("")
        recyclerStudent = findViewById(R.id.view)
        student = ArrayList()
        studentAdapter = Student_adapter(this, student)
        recyclerStudent.adapter = studentAdapter
        recyclerStudent.layoutManager = LinearLayoutManager(this)

        btn_insert.setOnClickListener {
            addNewStudent()
            input_mssv.setText("")
            input_name.setText("")
            input_class.setText("")
        }

        btn_del.setOnClickListener {
            var count = 0
            if (input_mssv.text.toString().isEmpty()) {
                db.deleteAllStudent()
            } else {
                for (st in db.getAllStudent()) {
                    if (input_mssv.text.toString() == st.mssv) {
                        deleteStudent(input_mssv.text.toString())
                        count += 1
                        break
                    }
                }
                if (count == 0)
                    Toast.makeText(this@MainActivity, "MSSV không hợp lệ", Toast.LENGTH_LONG).show()
            }
            input_mssv.setText("")
            input_name.setText("")
            input_class.setText("")
        }

        btn_update.setOnClickListener {
            var count = 0
            for (st in db.getAllStudent()) {
                if (input_mssv.text.toString() == st.mssv) {
                    updateStudent(st)
                    count += 1
                    break
                }
            }
            if (count == 0)
                Toast.makeText(this@MainActivity, "MSSV không hợp lệ", Toast.LENGTH_LONG).show()
            input_mssv.setText("")
            input_name.setText("")
            input_class.setText("")
        }

        btn_query.setOnClickListener {
            student.clear()
            studentAdapter.notifyDataSetChanged()
            if (input_mssv.text.toString().isEmpty()) {
                student.addAll(db.getAllStudent())
                studentAdapter.notifyDataSetChanged()
            } else {
                var count = 0
                for (st in db.getAllStudent()) {
                    if (input_mssv.text.toString() == st.mssv) {
                        queryStudent(input_mssv.text.toString())
                        studentAdapter.notifyDataSetChanged()
                        count += 1
                        break
                    }
                }
                if (count == 0)
                    Toast.makeText(this@MainActivity, "MSSV không hợp lệ", Toast.LENGTH_SHORT)
                        .show()
            }
            input_mssv.setText("")
            input_name.setText("")
            input_class.setText("")
        }
    }

    private fun addNewStudent() {
        val id = input_mssv.text.toString()
        val name = input_name.text.toString()
        val sClass = input_class.text.toString()
        val student = Student(id, name, sClass)
        db.addStudent(student)
    }

    private fun deleteStudent(id: String) {
        db.deleteStudent(id)
    }

    private fun updateStudent(student: Student) {
        val name = input_name.text.toString()
        val sClass = input_class.text.toString()
        student.name = name
        student.classroom = sClass
        db.updateStudent(student)
    }

    private fun queryStudent(id: String) {
        student.add(db.getStudent(id))
    }
}