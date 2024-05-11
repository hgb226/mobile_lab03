package com.example.lab03_02_nangcao

class Student {
    var mssv: String = ""
    var name: String = ""
    var classroom: String = ""

    constructor()

    constructor(mssv: String, fullName: String, maLop: String) {
        this.mssv = mssv
        this.name = fullName
        this.classroom = maLop
    }
}