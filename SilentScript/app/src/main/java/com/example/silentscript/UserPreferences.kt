package com.example.mystoryapp.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>){
    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }

        private val TOKEN = stringPreferencesKey("token")
        private val UID = stringPreferencesKey("uid")
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN] ?: ""
        }
    }
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    suspend fun deleteToken() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN)
        }
    }

    suspend fun deleteUid() {
        dataStore.edit { preferences ->
            preferences.remove(UID)
        }
    }

    fun getUid(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[UID] ?: ""
        }
    }
    suspend fun saveUid(uid: String) {
        dataStore.edit { preferences ->
            preferences[UID] = uid
        }
    }
}