package com.solara.pofindr.ui.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solara.pofindr.data.model.PostOffice
import com.solara.pofindr.databinding.ItemPostOfficeBinding

class PostOfficeAdapter : RecyclerView.Adapter<PostOfficeAdapter.PostOfficeViewHolder>() {

    private var originalList = listOf<PostOffice>()
    private var filteredList = listOf<PostOffice>()

    fun submitList(list: List<PostOffice>) {
        originalList = list
        filteredList = list
        notifyDataSetChanged()
    }

    fun filter(query: String?) {
        filteredList = if (query.isNullOrEmpty()) {
            originalList
        } else {
            originalList.filter {
                it.Name.contains(query, true) || it.PINCode.contains(query, true)
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostOfficeViewHolder {
        val binding = ItemPostOfficeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostOfficeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostOfficeViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    class PostOfficeViewHolder(private val binding: ItemPostOfficeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(postOffice: PostOffice) {
            binding.postOfficeName.text = postOffice.Name
            binding.postOfficePinCode.text = postOffice.PINCode

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)
                intent.putExtra("postOffice", postOffice)
                binding.root.context.startActivity(intent)
            }
        }
    }
}