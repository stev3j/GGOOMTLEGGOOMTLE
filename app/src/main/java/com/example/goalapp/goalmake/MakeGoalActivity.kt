package com.example.goalapp.goalmake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.goalapp.HomeActivity
import com.example.goalapp.R
import com.example.goalapp.databinding.ActivityMakeGoalBinding
import com.example.goalapp.db.entity.Goal
import com.example.goalapp.db.viewModel
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

        //뒤로가기 버튼을 눌렀을 때
        binding.goalmakeToolbar.setNavigationOnClickListener {
            finish()
        }

        //확인 버튼을 눌렀을 때
        binding.btnConfirm.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {
        //입력된 값을 가져옴
        val goalName = et_goal_insert.text.toString()
        val goalDeadline = et_time_insert.text.toString()

        //입력됐는지 체크
        if(inputCheck(goalName, goalDeadline)){
            //User오브젝트 만들기
            val goal = Goal(0, goalName, goalDeadline)
            //데이터베이스에 데이터 넣기
            mUserViewModel.addGoal(goal)
            Toast.makeText(applicationContext, "목표가 추가되었습니다!", Toast.LENGTH_LONG).show()
            //돌아가기
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

