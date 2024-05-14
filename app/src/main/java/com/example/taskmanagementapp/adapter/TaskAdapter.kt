package com.example.taskmanagementapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagementapp.databinding.TaskLayoutBinding
import com.example.taskmanagementapp.fragments.HomeFragment
import com.example.taskmanagementapp.fragments.HomeFragmentDirections
import com.example.taskmanagementapp.model.Task

class TaskAdapter: RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(val itemBinding: TaskLayoutBinding): RecyclerView.ViewHolder(itemBinding.root)//attach xml file using item binding

    //DiffUtil checks if theres difference between two items,use to update without refreshing the entire data set
    private val differCallback = object : DiffUtil.ItemCallback<Task>(){//ItemCallback interface used by DiffUtil
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.taskDesc == newItem.taskDesc &&
                    oldItem.taskTitle == newItem.taskTitle
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)//smooth ui update
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {//define the view layout(xml)
        return TaskViewHolder(
            TaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)//inflate-create the View object hierarchy from the XML layout file specified by TaskLayoutBinding
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = differ.currentList[position]//define the position of task in recycler view

        holder.itemBinding.noteTitle.text = currentTask.taskTitle
        holder.itemBinding.noteDesc.text = currentTask.taskDesc

        holder.itemView.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragmentToEditTaskFragment(currentTask)
            it.findNavController().navigate(direction)

        }
    }
}