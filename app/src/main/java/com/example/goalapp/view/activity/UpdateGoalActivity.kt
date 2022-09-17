package com.example.goalapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.goalapp.databinding.ActivityUpdateGoalBinding
import com.example.goalapp.db.entity.Goal
import com.example.goalapp.viewmodel.viewModel
import kotlinx.android.synthetic.main.activity_make_goal.*

class UpdateGoalActivity : AppCompatActivity() {
    private val binding: ActivityUpdateGoalBinding by lazy { ActivityUpdateGoalBinding.inflate(layoutInflater) }
//
    private lateinit var data: Goal
//
    private lateinit var mUserViewModel: viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        data = intent.getParcelableExtra<Goal>("goalUpdate")!!
//
        binding.etGoalInsert.setText(data.goalName)
        binding.etTimeInsert.setText(data.goalDeadline)

        mUserViewModel = ViewModelProvider(this).get(viewModel::class.java)

        //뒤로가기 버튼을 눌렀을 때
        binding.goalmakeToolbar.setNavigationOnClickListener {
            finish() //?
        }

        //확인 버튼을 눌렀을 때
        binding.btnConfirm.setOnClickListener {
            updateItem()
        }
    }
    private fun updateItem(){
        //입력된 값을 가져옴
        val goalName = et_goal_insert.text.toString()
        val goalDeadline = et_time_insert.text.toString()

        //입력됐는지 체크
        if(inputCheck(goalName, goalDeadline)){
            val updatedUser = Goal(data.goalId, 0, goalName = goalName, goalDeadline = goalDeadline, isAllChecked = "미달성")
            //업데이트
            mUserViewModel.updateGoal(updatedUser)
            Toast.makeText(applicationContext, "목표가 수정되었습니다!", Toast.LENGTH_LONG).show()
            //돌아가기
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }else{
            Toast.makeText(applicationContext, "빈칸을 채워주세요!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(goalName: String, goalDeadline: String): Boolean{
        return !(TextUtils.isEmpty(goalName)) && !(TextUtils.isEmpty(goalDeadline))
    }
}

