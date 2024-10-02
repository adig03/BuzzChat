package com.example.easychat.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.easychat.utils.User

class MainViewModel: ViewModel() {

    val userRepo = UserRepo()

    //getting all the users
    fun getUsers():LiveData<List<User>>{
        return userRepo.getUsers()
    }
}