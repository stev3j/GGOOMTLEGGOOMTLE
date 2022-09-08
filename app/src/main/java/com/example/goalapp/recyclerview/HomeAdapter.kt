package com.example.goalapp.recyclerview

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.goalapp.GoalActivity
import com.example.goalapp.R
import com.example.goalapp.databinding.GoalBlockBinding
import com.example.goalapp.db.entity.Goal


class HomeAdapter(val all_check_callback: (success: String) -> Unit) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private var goalList = listOf<Goal>()

    inner class MyViewHolder(private val binding: GoalBlockBinding): RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context
        fun bind(item: Goal) {
//            all_check_callback.invoke()

            //아래 처럼 만들어야함.. (Goal 안의 목표를 전부 달성하였을 때)
            binding.tvGoalSuccess.text = "달성"
            binding.tvGoalSuccess.setTextColor(ContextCompat.getColor(context!!, R.color.BackgroundGreen))

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