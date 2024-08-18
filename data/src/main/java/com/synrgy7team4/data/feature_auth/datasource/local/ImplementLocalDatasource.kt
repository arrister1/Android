package com.synrgy7team4.data.feature_auth.datasource.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.synrgy7team4.domain.feature_auth.model.response.LoginDataDomain
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class ImplementLocalDatasource(
    private val dataStore: DataStore<Preferences>
) : AuthLocalDatasource {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    override suspend fun saveTokens(jwtToken: String, refreshToken: String) {
        dataStore.edit {
            it[TOKEN_KEY] = jwtToken
            it[REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun loadJwtToken(): String? =
        dataStore.data.map {
            it[TOKEN_KEY]
        }.firstOrNull()

    override suspend fun deleteJwtToken() {
        dataStore.edit {
            it.remove(TOKEN_KEY)
        }
    }
}