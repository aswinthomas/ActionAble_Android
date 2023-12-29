package com.aswindev.actionable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aswindev.actionable.databinding.ActivityMainBinding
import com.google.android.material.motion.MaterialMainContainerBackHelper
import com.google.android.material.tabs.TabLayoutMediator

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

    }

    inner class PagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
        override fun getItemCount()=NUM_TABS

        override fun createFragment(position: Int): Fragment {
            return TasksFragment()
        }

    }
}