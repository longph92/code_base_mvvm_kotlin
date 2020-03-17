package com.example.demo_androidx

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_androidx.databinding.ItemUserInfoBinding
import com.example.demo_androidx.repository.model.User

class Adapter(private val users: ArrayList<User> = arrayListOf()): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users)
    }

    fun update(data: List<User>) {
        this.users.addAll(data)
        notifyItemRangeInserted(0, data.size)
    }
}

class ViewHolder(private val item: ItemUserInfoBinding) : RecyclerView.ViewHolder(item.root) {
    fun bind(users: List<User>) {
        item.name = "My name is: ${users[adapterPosition].firstName} ${users[adapterPosition].lastName}"
    }
}