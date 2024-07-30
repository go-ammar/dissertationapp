package com.project.collabexpense.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.project.collabexpense.utils.Constants.AUTO_AUTH_PREFS

object DataStoreSingleton {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AUTO_AUTH_PREFS)

    fun getInstance(context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}