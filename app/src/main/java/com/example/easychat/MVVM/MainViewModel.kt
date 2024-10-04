package com.example.easychat.MVVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.easychat.utils.SharedPrefs
import com.example.easychat.utils.User
import com.example.easychat.utils.loggedInUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val firestore = FirebaseFirestore.getInstance()
    val name = MutableLiveData<String?>()
    val imageUrl = MutableLiveData<String?>()
    val message = MutableLiveData<String>()
    val userRepo = UserRepo()

    init{
        getCurrentUser()
    }


    fun getUsers(): LiveData<List<User>> {
        return userRepo.getUsers()
    }


    fun getCurrentUser() = viewModelScope.launch(Dispatchers.IO) {

        val context = getApplication<Application>().applicationContext

        firestore.collection("Users")
            .document(loggedInUser.getLoggedInUser())
            .addSnapshotListener { value, e ->
                if (value != null && value.exists()) {
                    val user = value.toObject(User::class.java)


                    name.postValue(user?.user_name)
                    imageUrl.postValue(user?.imageUrl)


                    val mySharedPrefs = SharedPrefs(context)
                    mySharedPrefs.setValue("user_name" , user?.user_name!!)


                } else if (e != null) {
                    message.postValue("Error fetching user: ${e.message}")
                }
            }
    }
}
