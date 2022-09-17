package com.example.goalapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.goalapp.db.DataBase
import com.example.goalapp.db.entity.Goal
import com.example.goalapp.db.entity.TodayGoal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//호출을 조금더 쉽게 하기 위해 ViewModel을 만듦
class viewModel(application: Application): AndroidViewModel(application) {

    val readGoalData: LiveData<List<Goal>>
    val readTodayGoalData: LiveData<List<TodayGoal>>
    private val repository: Repository

    //시작했을 때
    init {
        val goalDao = DataBase.invoke(application).detailDao()
        repository = Repository(goalDao)
        readGoalData = repository.readGoalData
        readTodayGoalData = repository.readTodayGoalData
    }

    //추가
    fun addGoal(goal: Goal){
        //IO : 내부 DB 접근할 때 사용됨
        viewModelScope.launch(Dispatchers.IO) {
            repository.addGoal(goal)
        }
    }

    fun addTodayGoal(goal: TodayGoal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTodayGoal(goal)
        }
    }

    //수정
    fun updateGoal(goal: Goal){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateGoal(goal)
        }
    }

    //삭제
    fun deleteGoal(goal: Goal){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGoal(goal)
        }
    }

    fun deleteAllGoal(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllGoals()
        }
    }

    fun deleteTodayGoal(goal: TodayGoal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodayGoal(goal)
        }
    }

    //구분
    fun searchDatabase(searchId: Int): LiveData<List<TodayGoal>> {
        return repository.searchDatabase(searchId)
    }
}