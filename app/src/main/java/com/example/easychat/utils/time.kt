package com.example.easychat.utils

import java.text.SimpleDateFormat
import java.util.Date

class time {

    companion object{
        fun getTime():String{
            val formatter = SimpleDateFormat("HH:mm:ss")
            val date:Date = Date(System.currentTimeMillis())
            val stringDate = formatter.format(date)

            return stringDate
        }
    }
}