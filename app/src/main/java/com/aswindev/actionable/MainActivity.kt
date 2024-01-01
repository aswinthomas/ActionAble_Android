package com.aswindev.actionable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aswindev.actionable.data.ActionAbleDatabase
import com.aswindev.actionable.data.Task
import com.aswindev.actionable.databinding.ActivityMainBinding
import com.aswindev.actionable.databinding.DialogAddTaskBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    companion object {
        const val NUM_TABS = 1
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = PagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = "Tasks"
        }.attach()

        setAddTaskDialog()

        val database = ActionAbleDatabase.createDatabase(this)
        val taskDao = database.getTaskDao()

        lifecycleScope.launch {
            taskDao.createTask(Task(title = "Another Task"))
            taskDao.getAllTasks()
        }

    }

    private fun setAddTaskDialog() {
        binding.fab.setOnClickListener {
            val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)
            MaterialAlertDialogBuilder(this)
                .setTitle("Add new task")
                .setView(dialogBinding.root)
                .setPositiveButton("Save") { _, _ ->
                    Toast.makeText(this, "Your task is: ${dialogBinding.editText.text}", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount() = NUM_TABS

        override fun createFragment(position: Int): Fragment {
            return TasksFragment()
        }

    }
}