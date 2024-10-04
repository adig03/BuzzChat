package com.example.easychat.LoginRegister.fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.easychat.R
import com.example.easychat.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Suppress("DEPRECATION")
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    val signUpAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var SignUpPD: ProgressDialog
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = FirebaseFirestore.getInstance()
        SignUpPD = ProgressDialog(activity)

        binding.signUpTextToSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        binding.signUpBtn.setOnClickListener {
            val email = binding.signUpEmail.text.toString()
            val name = binding.signUpEtName.text.toString()
            val Password = binding.signUpPassword.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(activity, "Name Cant be Empty", Toast.LENGTH_SHORT).show()
            }
            if (Password.isEmpty()) {
                Toast.makeText(activity, "Password Cant be Empty", Toast.LENGTH_SHORT).show()
            }
            if (email.isEmpty()) {
                Toast.makeText(activity, "Email Cant be Empty", Toast.LENGTH_SHORT).show()
            }


            if (name.isNotEmpty() && email.isNotEmpty() && Password.isNotEmpty()) {
                getTheUserSignUp(name, email, Password)
            }


        }
    }

    private fun getTheUserSignUp(name: String, email: String, password: String) {
        SignUpPD.show()
        SignUpPD.setMessage("Creating Account")

        signUpAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                signUpAuth.currentUser?.sendEmailVerification()
                    ?.addOnSuccessListener {
                        Toast.makeText(
                            activity,
                            "Verification mail has been sent , Please verify your mail",
                            Toast.LENGTH_SHORT
                        ).show()
                    }?.addOnFailureListener {
                        Toast.makeText(activity, "exception: ${it.toString()} ", Toast.LENGTH_SHORT)
                            .show()
                    }


                val user = signUpAuth.currentUser

                val hashMap = hashMapOf(
                    "userId" to user!!.uid,
                    "user_name" to name,
                    "user_email" to email,
                    "status" to "default",
                    "imageUrl" to "https://tse3.mm.bing.net/th?id=OIP.hXZi-2Lc_OPdbDXIR_MSNQHaHa&pid=Api&P=0&h=180"
                )


                firestore.collection("Users").document(user.uid).set(hashMap)
                SignUpPD.dismiss()
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            }
        }
    }
}

