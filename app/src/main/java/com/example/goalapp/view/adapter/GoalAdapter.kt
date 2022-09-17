package com.example.goalapp.view.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.goalapp.R
import com.example.goalapp.databinding.TodayGoalBlockBinding
import com.example.goalapp.db.entity.TodayGoal
import kotlinx.android.synthetic.main.today_goal_block.view.*

class GoalAdapter(private val check_callback: (todayGoal: TodayGoal) -> Unit, private val todayGoalList: List<TodayGoal>) : RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    //ViewHolder : 각 view들을 담고있음
    inner class GoalViewHolder(private val binding: TodayGoalBlockBinding): RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(item: TodayGoal) {
            binding.items = item

            checkGoal(item, binding, context)

            binding.todayGoalBlock.iv_checkbox.setOnClickListener {
                changeCheckValue(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val binding = TodayGoalBlockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.bind(todayGoalList[position])
    }

    override fun getItemCount(): Int = todayGoalList.size

    fun changeCheckValue(item: TodayGoal){
        if (!item.isChecked) {
            val todayGoal = TodayGoal(item.id, item.todayGoalId, item.todayGoalName, true)
            check_callback.invoke(todayGoal)
        }
        else if(item.isChecked) {
            val todayGoal = TodayGoal(item.id, item.todayGoalId, item.todayGoalName, false)
            check_callback.invoke(todayGoal)
        }
    }

    fun checkGoal(item: TodayGoal, binding: TodayGoalBlockBinding, context: Context){
        if (!item.isChecked) {
            binding.tvTodayGoalName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.tvTodayGoalName.setTextColor(ContextCompat.getColor(context, R.color.textColor))
        }
        else if(item.isChecked) {
            binding.tvTodayGoalName.paintFlags = 0
            binding.tvTodayGoalName.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }

}