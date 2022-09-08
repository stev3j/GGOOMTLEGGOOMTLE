package com.example.goalapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.goalapp.db.entity.Goal
import com.example.goalapp.db.entity.TodayGoal

@Database(
    entities = [Goal::class, TodayGoal::class],
    version = 1,
    exportSchema = false
)
abstract class database: RoomDatabase() {

    //추상 클래스의 함수(확정되지 않음)
    abstract fun detailDao(): dao

    //상속받아지는 클래스에서 모두 동일한 값이 들어감
    companion object{
        @Volatile
        //인스턴스 : 할당된 메모리(구체화된 객체)
        private var INSTANCE: database? = null
        private val lock = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(lock){
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            database::class.java,
            "GoalList"
        ).build()
    }
}

