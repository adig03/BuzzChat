package com.example.easychat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.easychat.utils.loggedInUser
import com.example.easychat.utils.time
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObjects

class MessageRepo {

    private val firestore = FirebaseFirestore.getInstance()

    // LiveData for messages
    private val _messages = MutableLiveData<List<Messages>>()
    val messages: LiveData<List<Messages>> get() = _messages

    // Fetch messages between logged-in user and a friend
    fun getMessage(friendId: String): LiveData<List<Messages>> {
        // Generate a unique chat ID by combining logged-in user and friend's ID
        val uniqueId = listOf(loggedInUser.getLoggedInUser(), friendId)
            .sorted()
            .joinToString(separator = "")

        firestore.collection("Messages")
            .document(uniqueId)
            .collection("chats")
            .orderBy("time" ,Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    // Log error if the listener fails
                    println("Error fetching messages: ${exception.message}")
                    return@addSnapshotListener
                }

                // Handle snapshot, map documents to Messages objects
                snapshot?.let { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val messageList = querySnapshot.toObjects(Messages::class.java)
                        _messages.value = messageList.filter { message ->
                            (message.sender == loggedInUser.getLoggedInUser() && message.receiver == friendId) ||
                                    (message.sender == friendId && message.receiver == loggedInUser.getLoggedInUser())
                        }
                    } else {
                        _messages.value = emptyList()
                    }
                }
            }

        return messages
    }
}
