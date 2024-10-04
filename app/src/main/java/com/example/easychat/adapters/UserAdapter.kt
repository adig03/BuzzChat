package com.example.easychat.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easychat.databinding.RvUsersItemBinding
import com.example.easychat.utils.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: RvUsersItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var users  = listOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvUsersItemBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
      val user = users[position]


        holder.binding.UserName.text = user.user_name
        holder.binding.UserEmail.text = user.user_email

        Glide.with(holder.itemView.context)
            .load(user.imageUrl)
            .into(holder.binding.userImage)


        holder.itemView.setOnClickListener {
            onItemClickListener?.let { click ->
                click(user)
            }
        }
    }


    private var onItemClickListener: ((User) -> Unit)? = null

    fun setOnItemClickListener(listener: (User) -> Unit) {
        onItemClickListener = listener
    }

    fun setUsers(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }
}
