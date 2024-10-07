package com.example.easychat.main.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easychat.ChatViewModel
import com.example.easychat.R
import com.example.easychat.databinding.FragmentProfileBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ProfileFragment : Fragment() {

private lateinit var binding : FragmentProfileBinding
val firestore = FirebaseFirestore.getInstance()
val auth = FirebaseAuth.getInstance()
    private lateinit var chatViewModel: ChatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userLoggedInEmail.setText(auth.currentUser?.email)

     chatViewModel = ViewModelProvider(requireActivity())[ChatViewModel::class.java]


        chatViewModel.imageUrl.observe(viewLifecycleOwner , Observer {
            Glide.with(requireContext()).load(it).into(binding.circleImageView)
        })
        chatViewModel.name.observe(viewLifecycleOwner , Observer {
           binding.profileNameEt.setText(it.toString())
        })


    }

    }


