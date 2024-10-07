package com.example.easychat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easychat.databinding.ChatitemrightBinding
import com.example.easychat.databinding.ChatitemleftBinding
import com.example.easychat.utils.loggedInUser

class MessageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listOfMessages = listOf<Messages>()
    private val left = 0
    private val right = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == right) {
            // Inflate the layout using View Binding for the right side
            val binding = ChatitemrightBinding.inflate(inflater, parent, false)
            RightMessageHolder(binding)
        } else {
            // Inflate the layout using View Binding for the left side
            val binding = ChatitemleftBinding.inflate(inflater, parent, false)
            LeftMessageHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return listOfMessages.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = listOfMessages[position]

        if (holder is RightMessageHolder) {
            holder.bind(message)
        } else if (holder is LeftMessageHolder) {
            holder.bind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (listOfMessages[position].sender == loggedInUser.getLoggedInUser()) right else left
    }

    fun setMessageList(newList: List<Messages>) {
        this.listOfMessages = newList
        notifyDataSetChanged()  // Notify adapter to refresh the list
    }

    inner class RightMessageHolder(private val binding: ChatitemrightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Messages) {
           binding.showMessage.text = message.message
            binding.timeView.text = message.time?.substring(0, 5) ?: ""
        }
    }

    inner class LeftMessageHolder(private val binding: ChatitemleftBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Messages) {
            binding.showMessage.text = message.message
            binding.timeView.text = message.time?.substring(0, 5) ?: ""
        }
    }
}
