package com.example.goalapp.db.entity

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize //string같은 기본형이 아니라 객체로 전달해야할 때 씀
@Entity(tableName = "goal_table")
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val goalId: Int,
    //data를 주고 받을 때 명시할 이름
    val goalIcon: Int,
    val goalName: String,
    val goalDeadline: String,
    val isAllChecked: Boolean
): Parcelable

