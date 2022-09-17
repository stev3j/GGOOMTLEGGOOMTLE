package com.example.goalapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.goalapp.db.DataBase
import com.example.goalapp.db.entity.Goal
import com.example.goalapp.db.entity.TodayGoal
import com.example.goalapp.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoalViewModel(application: Application): AndroidViewModel(application) {
    val readGoalData: MutableLiveData<List<Goal>>
    private val readTodayGoalData: MutableLiveData<List<TodayGoal>>
    private val repository: Repository

    init {
        val goalDao = DataBase.invoke(application).detailDao()
        repository = Repository(goalDao)
        readGoalData = repository.readGoalData
        readTodayGoalData = repository.readTodayGoalData
    }

    fun updateTodayGoal(goal: TodayGoal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodayGoal(goal)
        }
    }
    fun searchDatabase(searchId: Int): MutableLiveData<List<TodayGoal>> {
        return repository.searchDatabase(searchId)
    }

    fun deleteGoal(goal: Goal){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGoal(goal)
        }
    }

}