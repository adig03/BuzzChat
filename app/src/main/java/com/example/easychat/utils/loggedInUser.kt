package com.example.easychat.utils

import com.google.firebase.auth.FirebaseAuth

class loggedInUser {

    companion object{
        private val auth  = FirebaseAuth.getInstance()
        private var userId :String = ""

        fun getLoggedInUser():String {
            if(auth.currentUser!=null){
                userId = auth.currentUser!!.uid
            }

            return userId
        }


    }
}