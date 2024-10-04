package com.example.easychat.Main.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easychat.MVVM.MainViewModel
import com.example.easychat.R
import com.example.easychat.databinding.FragmentChatBinding


class ChatFragment : Fragment(R.layout.fragment_chat){

    private lateinit var binding:FragmentChatBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var args: ChatFragmentArgs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_chat , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args = ChatFragmentArgs.fromBundle(requireArguments())

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]




        Glide.with(requireContext()).load(args.Reciever.imageUrl).into(binding.chatImageViewUser)

        binding.chatUserName.text = args.Reciever.user_name.toString()




    }


}