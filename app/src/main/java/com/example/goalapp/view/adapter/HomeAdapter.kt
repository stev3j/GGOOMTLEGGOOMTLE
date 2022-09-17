package com.example.goalapp.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.goalapp.view.activity.GoalActivity
import com.example.goalapp.R
import com.example.goalapp.databinding.GoalBlockBinding
import com.example.goalapp.db.entity.Goal


class HomeAdapter(val all_check_callback: (success: String) -> Unit) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private var goalList = listOf<Goal>()

    inner class MyViewHolder(private val binding: GoalBlockBinding): RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context
        fun bind(item: Goal) {
//            all_check_callback.invoke()

            //Goal안에 들어가있는 todayGoal의 수가 1개 이상이고,
            //그 모든 todayGoal의 isChecked가 true일 때,
            //Home으로 intent를 보내 해당하는 Goal의 isAllChecked를 true로 변환!!!

            if(item.isAllChecked){
                binding.tvGoalSuccess.text = "달성"
                binding.tvGoalSuccess.setTextColor(ContextCompat.getColor(context!!, R.color.BackgroundGreen))
            }

            binding.tvGoalName.text = item.goalName
            binding.tvGoalDay.text = item.goalDeadline

            itemView.setOnClickListener {
                val intent = Intent(context, GoalActivity::class.java)
                intent.putExtra("goal", item)
                intent.run { context.startActivity(this) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GoalBlockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(goalList[position])
    }

    override fun getItemCount(): Int = goalList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(goal: List<Goal>){
        this.goalList = goal
        notifyDataSetChanged() //list 업데이트
    }
}