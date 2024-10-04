package com.example.easychat.Main.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.bumptech.glide.Glide
import com.example.easychat.MVVM.MainViewModel
import com.example.easychat.R

import com.example.easychat.adapters.UserAdapter
import com.example.easychat.adapters.queryAdapter
import com.example.easychat.databinding.FragmentHomeBinding



class HomeFragment : Fragment(R.layout.fragment_home) {

private lateinit var binding: FragmentHomeBinding
private lateinit var queryAdapter: queryAdapter
private lateinit var userAdapter: UserAdapter
private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_home, container , false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAdapter = UserAdapter()
        binding.profileImage.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment2_to_profileFragment)
        }

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        mainViewModel.userRepo.getUsers().observe(viewLifecycleOwner, Observer { users ->
            userAdapter.setUsers(users)
        })
        setUpRecyclerViewForQuery()

        setUpRecyclerViewForUsers()

     mainViewModel.imageUrl.observe(viewLifecycleOwner ,Observer{
         Glide.with(requireContext()).load(it).into(binding.profileImage)
     })
    }

    private fun setUpRecyclerViewForUsers() {
        userAdapter = UserAdapter()

        binding.rvUsersList.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    private fun setUpRecyclerViewForQuery() {

        val Queries: MutableList<String> = mutableListOf()


        Queries.add("All")
        Queries.add("Unread")
        Queries.add("Favourites")
        Queries.add("Groups")


        queryAdapter = queryAdapter(Queries)
        binding.rvQueryParameter.apply{
            adapter = queryAdapter
            layoutManager = GridLayoutManager(activity , 1 , HORIZONTAL , false)
        }
    }




}