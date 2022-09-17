package com.example.goalapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goalapp.R
import com.example.goalapp.databinding.ActivityHomeBinding
import com.example.goalapp.db.entity.Goal
import com.example.goalapp.viewmodel.viewModel
import com.example.goalapp.view.adapter.HomeAdapter
import com.example.goalapp.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter : HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        performViewModel()
        observeViewModel()

        //fab을 클릭했을 때
        homeViewModel.onClickFloatingEvent.observe(this) {
            startActivity(Intent(this, MakeGoalActivity::class.java))
        }

        //menu
        binding.homeToolbar.apply {
            inflateMenu(R.menu.home_menu)
            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.help ->
                        Toast.makeText(applicationContext, "도움말", Toast.LENGTH_SHORT).show()
                    R.id.setting ->
                        Toast.makeText(applicationContext, "설정", Toast.LENGTH_SHORT).show()
                    R.id.aaa ->
                        Toast.makeText(applicationContext, "문의", Toast.LENGTH_SHORT).show()
                }
                true
            }
        }
    }

    private fun observeViewModel() {
        with(homeViewModel) {
            readGoalData.observe(this@HomeActivity) {
                initHomeAdapter(it)
            }
        }
    }

    private fun performViewModel() {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding.vm = homeViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    private fun initHomeAdapter(items : List<Goal>) {
        with(binding.rvHome) {
            homeAdapter = HomeAdapter(items)
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }
    }
}