package com.example.alomari_hajar_mathstudy

import androidx.recyclerview.widget.RecyclerView
import com.example.alomari_hajar_mathstudy.databinding.ItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup




class RVAdapter(private val list: ArrayList<String>): RecyclerView.Adapter<RVAdapter.ViewHolder>() {
    class ViewHolder (val binding: ItemBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

      holder.binding.tvQ.text = item

    }

    override fun getItemCount()= list.size
}