package com.example.easychat.LoginRegister.fragments

import android.app.ProgressDialog
import android.media.tv.TvContract.Programs
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.easychat.R
import com.example.easychat.databinding.FragmentForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth


@Suppress("DEPRECATION")
class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {

    private lateinit var binding :FragmentForgotPasswordBinding
    val ForgotAuth =FirebaseAuth.getInstance()
    private lateinit  var forgotPD :ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = DataBindingUtil.inflate(inflater ,R.layout.fragment_forgot_password, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forgotPD = ProgressDialog(activity)


        binding.btnForgotPasswordBack.setOnClickListener{
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }



        binding.btnResetPassword.setOnClickListener{


            val email = binding.edtForgotPasswordEmail.text.toString()
            if(email.isNotEmpty()){
                sendTheUserResetEmail(email)
            }
            else{
                Toast.makeText(activity , "Please Enter a Verified Email" , Toast.LENGTH_SHORT).show()
            }

        }
        }

    private fun sendTheUserResetEmail(email:String?) {
        forgotPD.show()
        forgotPD.setMessage("Please wait")
        ForgotAuth.sendPasswordResetEmail(email!!).addOnSuccessListener {
            Toast.makeText(activity , " Please check your email" , Toast.LENGTH_SHORT).show()
            forgotPD.dismiss()

        }.addOnFailureListener{
            Toast.makeText(activity , "Something went wrong , Exception -> ${it.toString()}"
                , Toast.LENGTH_SHORT).show()
            forgotPD.dismiss()

        }
    }


}


