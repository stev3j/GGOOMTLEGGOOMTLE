package com.example.goalapp.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * 테이블(today_goal_table)
 */
@Parcelize //string같은 기본형이 아니라 객체로 전달해야할 때 씀
@Entity(
    tableName = "today_goal_table",
    foreignKeys = [
        ForeignKey(
            entity = Goal::class,
            parentColumns = ["goalId"],
            childColumns = ["id"],
            //Goal이 삭제되면 그 안에 있던 ToadayGoal들도 삭제되게
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TodayGoal(
    @SerializedName("id") val id: Int,
    @PrimaryKey(autoGenerate = true)
    @SerializedName("todayGoalId") val todayGoalId: Int,
    @SerializedName("todayGoalName") val todayGoalName: String,
    @SerializedName("isChecked") val isChecked: Boolean = false
): Parcelable