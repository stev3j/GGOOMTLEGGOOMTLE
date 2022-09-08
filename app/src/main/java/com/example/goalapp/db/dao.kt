package com.example.goalapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.goalapp.db.entity.Goal
import com.example.goalapp.db.entity.TodayGoal

@Dao
interface dao {

    //추가
    //onConflict : 중복된 Primary Key(id) 값이 존재할 경우 무시(IGNORE)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGoal(goal: Goal)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTodayGoal(goal: TodayGoal)

    //수정
    @Update
    suspend fun updateGoal(goal: Goal)

    @Update
    suspend fun updateTodayGoal(goal: TodayGoal)

    //삭제
    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Delete
    suspend fun deleteTodayGoal(goal: TodayGoal)

    // <-- 세부적인 기능을 만들기 위해서는 Query 어노테이션을 작성함 -->
    // <-- + SQL 문을 작성함 -->


    //today_gaol_table에 있는 id와 searchId가 같은 TodayGoal의 요소들을 보여주기
    @Query("SELECT * FROM today_goal_table WHERE id LIKE :searchId")
    fun searchDatabase(searchId: Int): LiveData<List<TodayGoal>>


    //모든 올해의 목표 삭제하기
    @Query("DELETE FROM goal_table")
    suspend fun deleteAllGoals()

    //추가한 데이터 읽기 기능
    @Query("SELECT * FROM goal_table ORDER BY goalId ASC")
    fun readGoalData(): LiveData<List<Goal>>

    @Query("SELECT * FROM today_goal_table ORDER BY todayGoalId ASC")
    fun readTodayGoalData(): LiveData<List<TodayGoal>>
}



