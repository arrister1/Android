package com.synrgy7team4.feature_dashboard.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.synrgy7team4.feature_dashboard.data.local.entity.UserEntity

@Dao
interface UserDataDao {

    @Query("SELECT * FROM userDataHome WHERE accountNumber = accountNumber")
    fun getUserData(accountNumber: String):LiveData<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(user: UserEntity)

    @Query("DELETE FROM userDataHome")
    suspend fun deleteUserData()


    @Query("SELECT * FROM userDataHome WHERE accountNumber = :accountNumber")
    fun getBalance(accountNumber: String): LiveData<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalance(balance: UserEntity)

    @Query("DELETE FROM userDataHome")
    suspend fun deleteAllBalances()
}