package com.project.collabexpense.data.local.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.project.collabexpense.data.local.prefs.PreferenceManager.PreferencesKeys.AUTH_TOKEN
import com.project.collabexpense.di.DataStoreSingleton
import com.project.collabexpense.utils.Constants.AUTH_TOKEN_PREF
import com.project.collabexpense.utils.Constants.AUTO_AUTH_PREFS
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PreferenceManager @Inject constructor(@ApplicationContext context: Context) {

//    private val Context._dataStore by preferencesDataStore(name = AUTO_AUTH_PREFS)
//    private val dataStore: DataStore<Preferences> = context._dataStore

    private val dataStore: DataStore<Preferences> = DataStoreSingleton.getInstance(context)

    private object PreferencesKeys {
        val AUTH_TOKEN = stringPreferencesKey(AUTH_TOKEN_PREF)
    }

    suspend fun saveAuth(auth: String) {
        dataStore.edit {
            it[AUTH_TOKEN] = auth
        }
    }

    suspend fun getAuth(): String? {
        val preferences = dataStore.data.first()
        return preferences[AUTH_TOKEN]
    }

    // Clear all preferences
    suspend fun clearPreferences() {
        dataStore.edit {
            it.clear()
        }
    }

}