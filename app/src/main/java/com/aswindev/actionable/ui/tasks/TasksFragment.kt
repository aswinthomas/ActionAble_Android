package com.aswindev.actionable.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import com.aswindev.actionable.data.ActionAbleDatabase
import com.aswindev.actionable.data.Task
import com.aswindev.actionable.data.TaskDao
import com.aswindev.actionable.databinding.FragmentTasksBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private val taskDao: TaskDao by lazy {
        val db = ActionAbleDatabase.getDatabase(requireContext())
        db.getTaskDao()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchAllTasks()
    }

    fun fetchAllTasks() {
        lifecycleScope.launch {
            val tasks = taskDao.getAllTasks()
            withContext(Dispatchers.Main) {
                binding.recyclerView.adapter = TasksAdapter(tasks = tasks)
            }
        }
    }

}