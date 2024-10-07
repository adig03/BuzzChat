package com.example.easychat.LoginRegister.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.easychat.main.MainActivity
import com.example.easychat.R
import com.example.easychat.databinding.FragmentIntroBinding
import com.google.firebase.auth.FirebaseAuth


class IntroFragment : Fragment(R.layout.fragment_intro) {

private lateinit var binding: FragmentIntroBinding
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_intro, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (auth.currentUser != null) {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }


        binding.loginButton.setOnClickListener{

            findNavController().navigate(R.id.action_introFragment_to_loginFragment)
        }
        binding.registerButton.setOnClickListener{

            findNavController().navigate(R.id.action_introFragment_to_signUpFragment)
        }
    }


}