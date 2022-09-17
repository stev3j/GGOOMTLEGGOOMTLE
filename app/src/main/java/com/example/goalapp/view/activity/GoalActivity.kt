package com.example.goalapp.view.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goalapp.R
import com.example.goalapp.databinding.ActivityGoalBinding
import com.example.goalapp.db.entity.Goal
import com.example.goalapp.db.entity.TodayGoal
import com.example.goalapp.viewmodel.viewModel
import com.example.goalapp.view.adapter.GoalAdapter
import com.example.goalapp.view.adapter.HomeAdapter
import com.example.goalapp.viewmodel.GoalViewModel
import com.example.goalapp.viewmodel.HomeViewModel

class GoalActivity : AppCompatActivity() {

    private val binding: ActivityGoalBinding by lazy { ActivityGoalBinding.inflate(layoutInflater) }
    private lateinit var data: Goal
    private lateinit var goalViewModel: GoalViewModel
    private lateinit var goalAdapter: GoalAdapter

    @RequiresApi(33)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        performViewModel()
        data = intent.getParcelableExtra("goal", Goal::class.java)!! //Home에서 들어간 Goal받기
        binding.goalToolbarTitle.text = data.goalName //title을 지금 들어가있는 goal의 goalName으로 바꿈

        goalViewModel.searchDatabase(searchId = data.goalId).observe(this) { list ->
            initGoalAdapter(list)
        }
        //뒤록가기 버튼을 눌렀을 때
        binding.goalToolbar.setNavigationOnClickListener {
            finish()
        }

//      //fab 눌렀을 때
        binding.fab.setOnClickListener {
            //todayGoal 내용 전달하기
            val intent = Intent(this, MakeTodayGoalActivity::class.java)
            intent.putExtra("goal", data)
            intent.run { this@GoalActivity.startActivity(this) }
        }

        //메뉴
        binding.goalToolbar.apply {
            //goal수정
            val intent = Intent(this@GoalActivity, UpdateGoalActivity::class.java)
            intent.putExtra("goalUpdate", data)
            //toolbarMenu를 클릭했을 때
            inflateMenu(R.menu.goal_menu)
            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.edit -> {
                        intent.run { this@GoalActivity.startActivity(this) }
                    }
                    R.id.delete ->
                        deleteGoal()
                }
                true
            }
        }
    }

    private fun deleteGoal() {
        //경고창? 띄우기
        val builder = AlertDialog.Builder(this)

        //Yes를 클릭했을 때
        builder.setPositiveButton("확인") { _, _ ->
            goalViewModel.deleteGoal(data)
            Toast.makeText(this, "목표가 삭제되었습니다", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
        }
        //No를 클릭했을 때
        builder.setNegativeButton("취소") { _, _ -> }

        builder.setTitle("목표를 삭제하시겠습니까?")
        builder.setMessage("오늘의 목표도 전부 삭제됩니다.")
        builder.create().show()
    }


    private fun performViewModel() {
        goalViewModel = ViewModelProvider(this)[GoalViewModel::class.java]
        binding.vm = goalViewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    private fun initGoalAdapter(items : List<TodayGoal>) {
        with(binding.rvGoal) {
            goalAdapter = GoalAdapter({goalViewModel.updateTodayGoal(it)}, items)
            adapter = goalAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }
    }
}