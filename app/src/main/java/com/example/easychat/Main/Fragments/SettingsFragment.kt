package com.example.easychat.Main.Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.easychat.LoginRegister.IntroActivity
import com.example.easychat.MVVM.MainViewModel
import com.example.easychat.R
import com.example.easychat.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth


@Suppress("DEPRECATION")
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding:FragmentSettingsBinding
    private lateinit var mainViewModel: MainViewModel
    val auth = FirebaseAuth.getInstance()
    private lateinit var  progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
       binding = DataBindingUtil.inflate(inflater ,R.layout.fragment_settings, container, false )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
         progressDialog = ProgressDialog(activity)
        binding.settingsToProfile.setOnClickListener{
            findNavController().navigate(R.id.action_settingsFragment_to_profileFragment)
        }

        mainViewModel.imageUrl.observe(viewLifecycleOwner , Observer {
            Glide.with(requireContext()).load(it).into(binding.profileImage)
        })
        mainViewModel.name.observe(viewLifecycleOwner , Observer {
            binding.loggedUsername.text = it.toString()
        })

        binding.logoutButton.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Logout")
            builder.setMessage("Are you sure you want to log out?")


            builder.setPositiveButton("Yes") { dialog, _ ->
                progressDialog.show()
                progressDialog.setMessage("Signing Out")
                auth.signOut()


                val intent = Intent(activity, IntroActivity::class.java)


                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)


                startActivity(intent)


                requireActivity().finish()

                dialog.dismiss()
            }


            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }


            val dialog = builder.create()
            dialog.show()
        }




    }

}