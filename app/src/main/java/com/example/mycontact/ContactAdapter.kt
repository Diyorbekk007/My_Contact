package com.example.mycontact

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mycontact.databinding.ItemContactBinding

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    private var differList = AsyncListDiffer(this, DIFFER_CALLBACK)
    private var itemClick: (data: ContactData, position: Int) -> Unit = { _, _ -> }
    private var itemLongClick: (data: ContactData) -> Unit = {}

    companion object {
        val DIFFER_CALLBACK = object : DiffUtil.ItemCallback<ContactData>() {
            override fun areItemsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ContactData, newItem: ContactData): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun submitList(ls: List<ContactData>) {
        differList.submitList(ls.toMutableList())
    }

    fun submitItemClickListener(listener: (data: ContactData, position: Int) -> Unit) {
        itemClick = listener
    }

    fun submitItemLongClickListener(listener: (data: ContactData) -> Unit) {
        itemLongClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = differList.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(differList.currentList[position])

    inner class ViewHolder(private var binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClick(differList.currentList[adapterPosition], adapterPosition)
            }
            binding.root.setOnLongClickListener {
                itemLongClick(differList.currentList[adapterPosition])
                true
            }
        }

        fun bind(data: ContactData) {
            binding.name.text = data.name
            binding.phone.text = data.phone
            when (data.gender) {
                Gender.MEN -> binding.image.setImageResource(R.drawable.men)
                Gender.WOMEN -> binding.image.setImageResource(R.drawable.women)
                Gender.UNKNOWN -> binding.image.setImageResource(R.drawable.no_one)
            }
        }
    }

}