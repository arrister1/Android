package com.synrgy7team4.feature_dashboard.data.local

import androidx.lifecycle.LiveData
import com.synrgy7team4.feature_dashboard.data.local.entity.UserEntity
import com.synrgy7team4.feature_dashboard.data.local.room.UserDataDao

class LocalDataSource(private val userDataDao: UserDataDao) {

    fun getUserData(accountNumber: String): LiveData<UserEntity>{
        return userDataDao.getUserData(accountNumber)
    }

    suspend fun insertUserData(user: UserEntity){
        userDataDao.insertUserData(user)
    }

    suspend fun deleteUserData(){
        userDataDao.deleteUserData()
    }

    fun getBalance(accountNumber: String): LiveData<UserEntity>{
        return userDataDao.getBalance(accountNumber)
    }

    suspend fun insertBalance(user: UserEntity){
        userDataDao.insertBalance(user)
    }

    suspend fun deleteBalance(){
        userDataDao.deleteAllBalances()
    }
}