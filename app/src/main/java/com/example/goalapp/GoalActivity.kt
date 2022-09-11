package com.example.goalapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goalapp.databinding.ActivityGoalBinding
import com.example.goalapp.db.entity.Goal
import com.example.goalapp.db.viewModel
import com.example.goalapp.goalmake.MakeTodayGoalActivity
import com.example.goalapp.goalmake.UpdateGoalActivity
import com.example.goalapp.recyclerview.GoalAdapter
import com.example.goalapp.recyclerview.HomeAdapter

class GoalActivity : AppCompatActivity() {

    private val binding: ActivityGoalBinding by lazy { ActivityGoalBinding.inflate(layoutInflater) }

    private lateinit var data: Goal

    private lateinit var mUserViewModel: viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val context = binding.root.context

        data = intent.getParcelableExtra<Goal>("goal")!! //Home에서 들어간 Goal받기

        mUserViewModel = ViewModelProvider(this).get(viewModel::class.java)

        binding.goalToolbarTitle.text = data.goalName //title을 지금 들어가있는 goal의 goalName으로 바꿈

        val goalAdapter = GoalAdapter { todayGoal ->
            mUserViewModel.updateTodayGoal(todayGoal)
        }

        binding.rv.apply {
            adapter = goalAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }

//        //지금 들어가 있는 id에 맞게 todayGoal 출력
        mUserViewModel.searchDatabase(searchId = data.goalId).observe(this) { list ->
            val intent = Intent(context, HomeAdapter::class.java)
            var checkCount = 0
            list.let {
                goalAdapter.setData(it)
                //들어가있는 todaygoal이 모두 체크되었다면
                /* error */
//                for(i: Int in 0..goalAdapter.itemCount){
//                    if(goalAdapter.)
//                        checkCount += 1
//                }
//                if(checkCount == goalAdapter.itemCount){
//                intent.putExtra("allChecked", true)
//                }
            }
        }



        //뒤록가기 버튼을 눌렀을 때
        binding.goalToolbar.setNavigationOnClickListener {
            finish()
        }

//      //fab 눌렀을 때
        binding.fab.setOnClickListener {
            //todayGoal 내용 전달하기
            val intent1 = Intent(context, MakeTodayGoalActivity::class.java)
            intent1.putExtra("goal", data)
            intent1.run { context.startActivity(this) }
        }

        //메뉴
        binding.goalToolbar.apply {
            //goal수정
            val intent2 = Intent(context, UpdateGoalActivity::class.java)
            intent2.putExtra("goalUpdate", data)
            //toolbarMenu를 클릭했을 때
            inflateMenu(R.menu.goal_menu)
            setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.edit -> {
                        intent2.run { context.startActivity(this) }
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
            mUserViewModel.deleteGoal(data)
            Toast.makeText(this, "목표가 삭제되었습니다", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
        }
        //No를 클릭했을 때
        builder.setNegativeButton("취소") { _, _ -> }

        builder.setTitle("목표를 삭제하시겠습니까?")
        builder.setMessage("오늘의 목표도 전부 삭제됩니다.")
        builder.create().show()
    }
}