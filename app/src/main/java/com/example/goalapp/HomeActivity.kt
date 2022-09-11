package com.example.goalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goalapp.databinding.ActivityHomeBinding
import com.example.goalapp.databinding.GoalBlockBinding
import com.example.goalapp.db.entity.Goal
import com.example.goalapp.db.entity.TodayGoal
import com.example.goalapp.db.viewModel
import com.example.goalapp.goalmake.MakeGoalActivity
import com.example.goalapp.goalmake.UpdateGoalActivity
import com.example.goalapp.recyclerview.GoalAdapter
import com.example.goalapp.recyclerview.HomeAdapter
import kotlin.properties.Delegates

class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
//    private val goalblock_binding: GoalBlockBinding by lazy { GoalBlockBinding.inflate(layoutInflater) }

    private lateinit var mUserViewModel: viewModel

//    private var data by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        data = intent.getBooleanExtra("allChecked", true) //Goal에서 부터 모두 체크됨을 받음

        val homeAdapter = HomeAdapter {} //HomeAdapter

        binding.rv.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }

        mUserViewModel = ViewModelProvider(this)[viewModel::class.java]
        mUserViewModel.readGoalData.observe(this, Observer { goal ->
            homeAdapter.setData(goal)
        })

        //fab을 클릭했을 때
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this, MakeGoalActivity::class.java))  // HomeActivity에서 MakeGoalActivity로 이동
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
}