package com.example.easychat.Main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.easychat.R
import com.example.easychat.databinding.FragmentProfileBinding
import com.example.easychat.utils.loggedInUser

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ProfileFragment : Fragment() {

private lateinit var binding : FragmentProfileBinding
val firestore = FirebaseFirestore.getInstance()
val auth = FirebaseAuth.getInstance()
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

        val currentUser = loggedInUser.getLoggedInUser()

        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid


        currentUserUid?.let { uid ->
            val userDocumentRef = firestore.collection("Users").document(uid)


            userDocumentRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {

                    val userName = documentSnapshot.getString("name")

                    binding.profileNameEt.setText(userName)
                } else {

                    binding.profileNameEt.setText("No name found")
                }
            }.addOnFailureListener { exception ->

                binding.profileNameEt.setText("Error retrieving name")
            }
        }
    }

    }


