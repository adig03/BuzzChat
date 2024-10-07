package com.example.easychat.main.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easychat.ChatViewModel
import com.example.easychat.R
import com.example.easychat.MessageAdapter
import com.example.easychat.databinding.FragmentChatBinding
import com.example.easychat.utils.loggedInUser

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var binding: FragmentChatBinding
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var args: ChatFragmentArgs
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args = ChatFragmentArgs.fromBundle(requireArguments())
        chatViewModel = ViewModelProvider(requireActivity())[ChatViewModel::class.java]

        args.Reciever.let { receiver ->
            Glide.with(requireContext())
                .load(receiver.imageUrl ?: "")
                .into(binding.chatImageViewUser)

            binding.chatUserName.text = receiver.user_name ?: "Unknown User"
        }

        binding.viewModel = chatViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.sendBtn.setOnClickListener {
            args.Reciever.let { receiver ->
                chatViewModel.sendMessages(
                    loggedInUser.getLoggedInUser(),
                    receiver.userId ?: return@setOnClickListener,
                    receiver.user_name ?: "Unknown User",
                    receiver.imageUrl ?: ""
                )
            }
        }

        messageAdapter = MessageAdapter()
        val layoutManager = LinearLayoutManager(context)
        layoutManager.stackFromEnd = true
        binding.messagesRecyclerView.layoutManager = layoutManager
        binding.messagesRecyclerView.adapter = messageAdapter

        chatViewModel.getMessages(args.Reciever.userId ?: return).observe(viewLifecycleOwner, Observer {
            messageAdapter.setMessageList(it ?: emptyList())
        })
    }
}
