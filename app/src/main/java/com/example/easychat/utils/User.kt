package com.example.easychat.utils

import android.os.Parcel
import android.os.Parcelable

data class User(

    val userId:String? = "",
    val user_name :String? = "",
    val user_email:String? = " ",
    val status :String? = "",
    val imageUrl :String? = ""

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(user_name)
        parcel.writeString(user_email)
        parcel.writeString(status)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
