package com.project.collabexpense.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import com.project.collabexpense.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceHelper @Inject constructor(@ApplicationContext context: Context){

    private val appPrefs: SharedPreferences = context.getSharedPreferences(Constants.SYNC_DATA_WORK_NAME, Context.MODE_PRIVATE)

    object PreferenceVariable {
        const val ACCESS_TOKEN = "access_token"
    }

    private val editor: SharedPreferences.Editor = appPrefs.edit()

    fun setOnChange(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        appPrefs.registerOnSharedPreferenceChangeListener(listener)
    }

    var authToken: String
        get() = appPrefs.getString(PreferenceVariable.ACCESS_TOKEN, "").toString()
        set(authToken) {
            editor.putString(PreferenceVariable.ACCESS_TOKEN, authToken)
            editor.apply()
        }

    fun clearPreferences() {
        editor.clear()
        editor.apply()
    }
}