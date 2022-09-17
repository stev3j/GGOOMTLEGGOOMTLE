package com.example.goalapp.viewmodel

import androidx.lifecycle.LiveData
import com.example.goalapp.db.dao
import com.example.goalapp.db.entity.Goal
import com.example.goalapp.db.entity.TodayGoal

//Dao의 기능들을 정리
class repository(private val goalDao: dao) {

    val readGoalData: LiveData<List<Goal>> = goalDao.readGoalData()
    val readTodayGoalData: LiveData<List<TodayGoal>> = goalDao.readTodayGoalData()
    //suspend : 비동기 환경에서 사용될 수 있다는 의미, 코루틴 내에서만 호출 가능
    //추가
    suspend fun addGoal(goal: Goal){
        goalDao.addGoal(goal)
    }

    suspend fun addTodayGoal(goal: TodayGoal) {
        goalDao.addTodayGoal(goal)
    }

    //수정
    suspend fun updateGoal(goal: Goal){
        goalDao.updateGoal(goal)
    }

    suspend fun updateTodayGoal(goal: TodayGoal) {
        goalDao.updateTodayGoal(goal)
    }

    //삭제
    suspend fun deleteGoal(goal: Goal){
        goalDao.deleteGoal(goal)
    }

    suspend fun deleteAllGoals(){
        goalDao.deleteAllGoals()
    }

    suspend fun deleteTodayGoal(goal: TodayGoal) {
        goalDao.deleteTodayGoal(goal)
    }

    //구분
    fun searchDatabase(searchId: Int): LiveData<List<TodayGoal>> {
        return goalDao.searchDatabase(searchId)
    }
}

