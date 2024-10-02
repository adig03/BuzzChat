package com.example.easychat.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.easychat.utils.User
import com.example.easychat.utils.loggedInUser
import com.google.firebase.firestore.FirebaseFirestore

class UserRepo {

    private val firestore = FirebaseFirestore.getInstance()

    fun getUsers(): LiveData<List<User>> {
        val users = MutableLiveData<List<User>>()

        firestore.collection("Users").addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            val userList = mutableListOf<User>()
            snapshot?.documents?.forEach { document ->
                val user = document.toObject(User::class.java)

                if (user!!.userId != loggedInUser.getLoggedInUser()) {
                    userList.add(user)
                }
            }

        users.value = userList
        }
        return users
    }
}
