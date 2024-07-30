package com.project.collabexpense.base

import androidx.appcompat.app.AppCompatActivity
import com.project.collabexpense.data.local.prefs.PreferenceHelper
import com.project.collabexpense.data.local.prefs.PreferenceManager
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var prefsManager: PreferenceManager

    @Inject
    lateinit var prefsHelper: PreferenceHelper

}