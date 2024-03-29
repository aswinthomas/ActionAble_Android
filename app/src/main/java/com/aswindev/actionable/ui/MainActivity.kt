package com.aswindev.actionable.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aswindev.actionable.data.ActionAbleDatabase
import com.aswindev.actionable.data.Task
import com.aswindev.actionable.data.TaskDao
import com.aswindev.actionable.databinding.ActivityMainBinding
import com.aswindev.actionable.databinding.DialogAddTaskBinding
import com.aswindev.actionable.ui.tasks.TasksFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    companion object {
        const val NUM_TABS = 1
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: ActionAbleDatabase
    private val taskDao: TaskDao by lazy {
        database = ActionAbleDatabase.getDatabase(this)
        database.getTaskDao()
    }
    private val tasksFragment: TasksFragment = TasksFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = PagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = "Tasks"
        }.attach()

        setAddTaskDialog()
    }

    private fun setAddTaskDialog() {
        binding.fab.setOnClickListener {
            val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(dialogBinding.root)
            dialogBinding.buttonShowDetails.setOnClickListener {
                dialogBinding.editTextTaskDetails.visibility =
                    if (dialogBinding.editTextTaskDetails.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
            dialogBinding.buttonSave.setOnClickListener {
                val task = Task(
                    title = dialogBinding.editTextTaskTitle.text.toString(),
                    description = dialogBinding.editTextTaskDetails.text.toString(),
                    isStarred = dialogBinding.buttonStarTask.isActivated)
                lifecycleScope.launch {
                    taskDao.createTask(task)
                }
                tasksFragment.fetchAllTasks()
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount() = NUM_TABS

        override fun createFragment(position: Int): Fragment {
            return tasksFragment
        }

    }
}