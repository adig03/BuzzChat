package com.example.easychat

data class Messages(
    val message: String? = "",
    val sender : String? = "",
    val receiver: String? = "",
    val time: String? = "",

) {

    val id : String get() = "$sender-$receiver-$message-$time"
}