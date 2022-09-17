package com.example.goalapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.goalapp.db.DataBase
import com.example.goalapp.db.entity.Goal
import com.example.goalapp.db.entity.TodayGoal
import com.example.goalapp.util.SingleLiveEvent

class HomeViewModel(application: Application): AndroidViewModel(application) {

    val onClickFloatingEvent = SingleLiveEvent<Any>()

    val readGoalData: MutableLiveData<List<Goal>>
    private val readTodayGoalData: MutableLiveData<List<TodayGoal>>
    private val repository: Repository

    init {
        val goalDao = DataBase.invoke(application).detailDao()
        repository = Repository(goalDao)
        readGoalData = repository.readGoalData
        readTodayGoalData = repository.readTodayGoalData
    }

    fun onClickFloatingBtn() {
        onClickFloatingEvent.call()
    }
}