package com.example.easychat.adapters

import android.media.RouteListingPreference
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easychat.databinding.RvQueryItemBinding


class queryAdapter(private val queryList: List<String>) : RecyclerView.Adapter<queryAdapter.queryViewHolder>() {

    inner class queryViewHolder( val binding: RvQueryItemBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): queryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvQueryItemBinding.inflate(inflater, parent, false) // Inflate layout
        return queryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: queryViewHolder, position: Int) {
        val query = queryList[position]
        holder.binding.queryButton.setText(query)


    }

    override fun getItemCount(): Int {
        return queryList.size // Return the total number of items
    }
}
