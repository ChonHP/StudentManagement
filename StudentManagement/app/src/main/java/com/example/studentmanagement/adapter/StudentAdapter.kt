package com.example.studentmanagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagement.databinding.StudentItemBinding
import com.example.studentmanagement.db.StudentEntity

class StudentAdapter: RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    private lateinit var binding: StudentItemBinding
    private lateinit var context: Context
    var onItemClick: ((StudentEntity) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = StudentItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: StudentAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder: RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StudentEntity) {
            binding.apply {
                txtFullName.text = item.fullName
                txtStudentIdentity.text = item.studentIdentity
            }
        }
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(differ.currentList[adapterPosition])
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<StudentEntity>() {
        override fun areItemsTheSame(oldItem: StudentEntity, newItem: StudentEntity): Boolean {
            return oldItem.studentId == newItem.studentId
        }

        override fun areContentsTheSame(oldItem: StudentEntity, newItem: StudentEntity): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

}