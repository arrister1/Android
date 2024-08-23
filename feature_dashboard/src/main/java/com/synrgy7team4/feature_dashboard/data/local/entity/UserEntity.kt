package com.synrgy7team4.feature_dashboard.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userDataHome")
data class UserEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "accountNumber")
    val accountNumber: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "balance")
    val balance: Double
)