package com.example.lab03_02_nangcao

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Student_adapter(private val mContext: Context, private val mStudent: List<Student>) :
    RecyclerView.Adapter<Student_adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val studentView = inflater.inflate(R.layout.listview_item, parent, false)
        return ViewHolder(studentView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = mStudent[position]
        holder.student.text = "${student.mssv} - ${student.name} - ${student.classroom}"
    }

    override fun getItemCount(): Int {
        return mStudent.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val student: TextView = view.findViewById(R.id.tvStudent)
    }
}