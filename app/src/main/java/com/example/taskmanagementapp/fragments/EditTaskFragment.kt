package com.example.taskmanagementapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskmanagementapp.MainActivity
import com.example.taskmanagementapp.R
import com.example.taskmanagementapp.databinding.FragmentEditTaskBinding
import com.example.taskmanagementapp.model.Task
import com.example.taskmanagementapp.viewmodel.TaskViewModel

class EditTaskFragment : Fragment(R.layout.fragment_edit_task), MenuProvider {

    private  var editTaskBinding: FragmentEditTaskBinding? = null
    private val binding get() = editTaskBinding

    private lateinit var tasksViewModel: TaskViewModel
    private lateinit var currentTask: Task

    private val args: EditTaskFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editTaskBinding =  FragmentEditTaskBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        tasksViewModel = (activity as MainActivity).taskViewModel
        currentTask = args.task!!

        binding?.editNoteTitle?.setText(currentTask.taskTitle)
        binding?.editNoteDesc?.setText(currentTask.taskDesc)

        binding?.editNoteFab?.setOnClickListener{
            val taskTitle = binding!!.editNoteTitle.text.toString().trim()
            val taskDesc = binding!!.editNoteDesc.text.toString().trim()

            if (taskTitle.isNotEmpty()){
                val task = Task(currentTask.id, taskTitle, taskDesc)
                tasksViewModel.updateTask(task)
                view.findNavController().popBackStack(R.id.homeFragment,false)
            }else{
                Toast.makeText(context, "Enter Title", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteTask(){
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Task")
            setMessage("Do you want to delete this task?")
            setPositiveButton("Delete"){_,_ ->
                tasksViewModel.deleteTask(currentTask)
                Toast.makeText(context,"Task Deleted", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
                setNegativeButton("Cancel", null)
            }.create().show()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_task, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deleteTask()
                true
            }else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editTaskBinding = null
    }

}