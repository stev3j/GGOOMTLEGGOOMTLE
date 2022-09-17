package com.example.goalapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.goalapp.databinding.ActivityMakeTodayGoalBinding
import com.example.goalapp.db.entity.Goal
import com.example.goalapp.db.entity.TodayGoal
import com.example.goalapp.viewmodel.viewModel
import kotlinx.android.synthetic.main.activity_make_today_goal.*

class MakeTodayGoalActivity : AppCompatActivity() {
    //binding
    private val binding: ActivityMakeTodayGoalBinding by lazy { ActivityMakeTodayGoalBinding.inflate(layoutInflater) }

    private lateinit var data: Goal

    //viewModelㅕ
    private lateinit var mUserViewModel: viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //데이터 전달받기
        data = intent.getParcelableExtra<Goal>("goal")!!

        //뒤로가기 버튼을 눌렀을 때
        binding.todaygoalmakeToolbar.setNavigationOnClickListener {
            finish()
        }

        //viewModel
        mUserViewModel = ViewModelProvider(this).get(viewModel::class.java)

        //확인 버튼을 클릭했을 때
        binding.todayOkBtn.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {
        //입력된 값을 가져옴
        val goalName = et_today_goalInsert.text.toString()

        //입력됐는지 체크
        if(inputCheck(goalName)){
            //User오브젝트 만들기
            val goal = TodayGoal(data.goalId, 0, goalName) /* error */
            //데이터베이스에 데이터 넣기
            mUserViewModel.addTodayGoal(goal)
            Toast.makeText(applicationContext, "오늘의 목표가 추가되었습니다!", Toast.LENGTH_LONG).show()
            //돌아가기
            finish()
        }else{
            Toast.makeText(applicationContext, "목표명을 채워주세요!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(goalName: String): Boolean{
        //TextUtils.isEmpty() : null체크
        return !(TextUtils.isEmpty(goalName))
    }
}

