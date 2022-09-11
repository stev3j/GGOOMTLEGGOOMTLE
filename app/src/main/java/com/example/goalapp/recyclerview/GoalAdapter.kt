package com.example.goalapp.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Message
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.goalapp.R
import com.example.goalapp.databinding.TodayGoalBlockBinding
import com.example.goalapp.db.entity.TodayGoal
import kotlinx.android.synthetic.main.today_goal_block.view.*

class GoalAdapter(private val check_callback: (todayGoal: TodayGoal) -> Unit) : RecyclerView.Adapter<GoalAdapter.MyViewHolder2>() {

    private var todayGoalList = listOf<TodayGoal>()

    //ViewHolder : 각 view들을 담고있음
    inner class MyViewHolder2(private val binding: TodayGoalBlockBinding): RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(item: TodayGoal) {
            binding.tvTodayGoalName.text = item.todayGoalName

            checkGoal(item, binding, context)

            binding.todayGoalBlock.iv_checkbox.setOnClickListener {
                changeCheckValue(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        val binding = TodayGoalBlockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder2(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {
        holder.bind(todayGoalList[position])
    }

    override fun getItemCount(): Int = todayGoalList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(todayGoal: List<TodayGoal>){
        this.todayGoalList = todayGoal
        notifyDataSetChanged() //list 업데이트
    }

    fun getItem(position: Int): TodayGoal {
        return this.todayGoalList[position]
    }

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
            binding.ivCheckbox.setImageResource(R.drawable.checkedbox)
        }
        else if(item.isChecked) {
            binding.tvTodayGoalName.paintFlags = 0
            binding.tvTodayGoalName.setTextColor(ContextCompat.getColor(context, R.color.black))
            binding.ivCheckbox.setImageResource(R.drawable.checkbox)
        }
    }

}