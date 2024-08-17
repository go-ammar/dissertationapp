package com.project.collabexpense.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.ArrayMap
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.ai.client.generativeai.GenerativeModel
import com.intuit.sdp.BuildConfig
import com.project.collabexpense.base.BaseActivity
import com.project.collabexpense.data.local.prefs.PreferenceHelper
import com.project.collabexpense.databinding.ActivityLoginBinding
import com.project.collabexpense.presentation.viewmodel.LoginViewModel
import com.project.collabexpense.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        var keepSplashOnScreen = true
        val delay = 2000L
        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({
            keepSplashOnScreen = false
            actionViews()
        }, delay)

        setContentView(binding.root)

//        lifecycleScope.launch {
//            ai()
//        }
    }

    private fun actionViews() {

        binding.signupLink.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginButton.setOnClickListener {

            val jsonParams: MutableMap<String?, Any?> = ArrayMap()
            jsonParams["email"] = binding.username.text.toString()

            jsonParams["password"] = binding.password.text.toString()

            val body = JSONObject(jsonParams).toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            lifecycleScope.launch {
                viewModel.loginAuth(body)
                viewModel.dataLocal.collect {
                    when (it) {
                        is Resource.Loading -> {
                            // Show loading state
//                        binding.progressBar.visibility = View.VISIBLE
                        }

                        is Resource.Success -> {
                            // Show data
//                        binding.progressBar.visibility = View.GONE
                            // Update your UI with the data
                            Log.d("TAG", "actionViews: "+it.data)
//                            prefsManager.saveAuth(it.data?.accessToken.toString())
                            PreferenceHelper(this@LoginActivity).authToken =
                                it.data?.accessToken.toString()
                            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        is Resource.Error -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                prefsManager.clearPreferences()
                            }
                            prefsHelper.clearPreferences()
                            // Show error message
//                        binding.progressBar.visibility = View.GONE
//                        binding.errorTextView.visibility = View.VISIBLE
//                        binding.errorTextView.text = resource.message
                        }
                    }
                }


            }


        }
    }

//    suspend fun ai(){
//        val generativeModel = GenerativeModel(
//            // For text-only input, use the gemini-pro model
//            modelName = "gemini-1.5-flash",
//            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
//            apiKey = "AIzaSyDS1Lfiqri47w6zi6j-r6tyZtROkEIHzy8"
//        )
//
//        val prompt = "Write a story about a magic backpack."
//        val response = generativeModel.generateContent(prompt)
//        print("AI response : --- "+response.text)
//    }
}