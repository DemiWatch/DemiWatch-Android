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
class LocalDataSource @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }

    suspend fun saveIdUser(userId: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    suspend fun saveIdPatient(patientId: String) {
        dataStore.edit { preferences ->
            preferences[PATIENT_ID_KEY] = patientId
        }
    }

    suspend fun saveHomeLocationPatient(homeLocation: String) {
        dataStore.edit { preferences ->
            preferences[PATIENT_HOME_KEY] = homeLocation
        }
    }

    suspend fun saveDestinationLocationPatient(destinationLocation: String) {
        dataStore.edit { preferences ->
            preferences[PATIENT_DESTINATION_KEY] = destinationLocation
        }
    }

    suspend fun cachePatientProfile(patientProfile: String) {
        dataStore.edit { preferences ->
            preferences[PATIENT_PROFILE_CACHE_KEY] = patientProfile
        }
    }

    fun getUserToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_TOKEN_KEY] ?: ""
        }
    }

    fun getIdUser(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID_KEY] ?: ""
        }
    }

    fun getIdPatient(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[PATIENT_ID_KEY] ?: ""
        }
    }

    fun getHomeLocationPatient(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[PATIENT_HOME_KEY] ?: ""
        }
    }

    fun getDestinationLocationPatient(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[PATIENT_DESTINATION_KEY] ?: ""
        }
    }

    fun getCachePatientProfile(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[PATIENT_PROFILE_CACHE_KEY] ?: ""
        }
    }

    suspend fun deleteTokenUser() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token_key")
        private val USER_ID_KEY = stringPreferencesKey("user_id_key")
        private val PATIENT_ID_KEY = stringPreferencesKey("patient_id_key")
        private val PATIENT_HOME_KEY = stringPreferencesKey("patient_home_key")
        private val PATIENT_DESTINATION_KEY = stringPreferencesKey("patient_destination_key")
        private val PATIENT_PROFILE_CACHE_KEY = stringPreferencesKey("patient_profile_key")
    }
}