package com.project.demiwatch.core.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val dataStore: DataStore<Preferences>)
{
    suspend fun saveUserToken(token: String){
        dataStore.edit{preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }

    fun getUserToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_TOKEN_KEY] ?: ""
        }
    }

    suspend fun deleteTokenUser(){
        dataStore.edit {
            it.clear()
        }
    }

    companion object{
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token_key")
    }
}