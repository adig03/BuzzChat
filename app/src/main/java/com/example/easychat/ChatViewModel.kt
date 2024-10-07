package com.example.easychat

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.easychat.utils.SharedPrefs
import com.example.easychat.utils.User
import com.example.easychat.utils.loggedInUser
import com.example.easychat.utils.time
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    val firestore = FirebaseFirestore.getInstance()
    val name = MutableLiveData<String?>()
    val imageUrl = MutableLiveData<String?>()
    val message = MutableLiveData<String>().apply { value = "" }
    val userRepo = UserRepo()
    val messageRepo = MessageRepo()

    init {
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
                    mySharedPrefs.setValue("user_name", user?.user_name!!)
                } else if (e != null) {
                    message.postValue("Error fetching user: ${e.message}")
                }
            }
    }

    fun sendMessages(sender: String, receiver: String, friendName: String, friendImage: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val context = getApplication<Application>().applicationContext

            val hashMap = hashMapOf<String, Any>(
                "sender" to sender,
                "receiver" to receiver,
                "message" to message.value!!,
                "time" to time.getTime()
            )

            val uniqueId = listOf(sender, receiver).sorted().joinToString(separator = "")

            val friendNamesplit = friendName.split("\\s".toRegex())[0]

            val mySharedPrefs = SharedPrefs(context)
            mySharedPrefs.setValue("friendId", receiver)
            mySharedPrefs.setValue("chatRoomId", uniqueId)
            mySharedPrefs.setValue("friendName", friendNamesplit)
            mySharedPrefs.setValue("friendimage", friendImage)

            // Save message in the Messages collection
            firestore.collection("Messages")
                .document(uniqueId.toString())
                .collection("chats")
                .document(time.getTime())
                .set(hashMap)
                .addOnCompleteListener { task ->

                    val hashMapForRecent = hashMapOf<String, Any>(
                        "friendId" to receiver,
                        "time" to time.getTime(),
                        "sender" to loggedInUser.getLoggedInUser(),
                        "message" to message.value!!,
                        "friendImage" to friendImage,
                        "name" to friendName,
                        "person" to "you"
                    )

                    // Store conversation for logged-in user
                    firestore.collection("Conversations${loggedInUser.getLoggedInUser()}")
                        .document(receiver)
                        .set(hashMapForRecent)

                    // Store conversation for receiver
                    firestore.collection("Conversations ${receiver}")
                        .document(loggedInUser.getLoggedInUser())
                        .update(
                            "message", message.value!!,
                            "time", time.getTime(),
                            "person", name.value!!
                        )

                    if (task.isSuccessful) {
                        message.value = ""
                    }
                }
        }

    fun getMessages(friendId: String): LiveData<List<Messages>> {
        return messageRepo.getMessage(friendId)
    }
}
