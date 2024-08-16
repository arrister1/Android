package com.synrgy7team4.common

import android.content.Context

class SharedPrefHelper(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun getJwtToken(): String? {
        return sharedPreferences.getString("jwt_token", null)
    }

    fun saveJwtToken(token: String?) {
        val editor = sharedPreferences.edit()
        editor.putString("jwt_token", token)
        editor.apply()
    }

    fun deleteJwtToken() {
        val editor = sharedPreferences.edit()
        editor.remove("jwt_token")
        editor.apply()
    }
}
