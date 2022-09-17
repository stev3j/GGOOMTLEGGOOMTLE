package com.example.goalapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.goalapp.databinding.ActivityMakeGoalBinding
import com.example.goalapp.db.entity.Goal
import com.example.goalapp.viewmodel.viewModel
import kotlinx.android.synthetic.main.activity_make_goal.*

class MakeGoalActivity : AppCompatActivity() {
    private val binding: ActivityMakeGoalBinding by lazy { ActivityMakeGoalBinding.inflate(layoutInflater) }

//    private lateinit var data: Goal

    private lateinit var mUserViewModel: viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        data = intent.getParcelableExtra<Goal>("goalUpdate")!!

//        binding.etGoalInsert.setText(data.goalName)
//        binding.etTimeInsert.setText(data.goalDeadline)

        mUserViewModel = ViewModelProvider(this).get(viewModel::class.java)

        binding.goalmakeToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnConfirm.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {
        val goalName = et_goal_insert.text.toString()
        val goalDeadline = et_time_insert.text.toString()

        if(inputCheck(goalName, goalDeadline)){
            val goal = Goal(0, goalName, goalDeadline, false)

            mUserViewModel.addGoal(goal)
            Toast.makeText(applicationContext, "목표가 추가되었습니다!", Toast.LENGTH_LONG).show()

            finish()
        }else{
            Toast.makeText(applicationContext, "빈칸을 채워주세요!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(goalName: String, goalDeadline: String): Boolean{
        //TextUtils.isEmpty() : null체크
        return !(TextUtils.isEmpty(goalName)) && !(TextUtils.isEmpty(goalDeadline))
    }
}

