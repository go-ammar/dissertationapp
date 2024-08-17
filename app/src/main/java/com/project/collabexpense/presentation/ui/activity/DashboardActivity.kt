package com.project.collabexpense.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.project.collabexpense.R
import com.project.collabexpense.base.BaseActivity
import com.project.collabexpense.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : BaseActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavigations()
    }

    private fun setBottomNavigations() {

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigation.itemIconTintList = null
        binding.bottomNavigation.selectedItemId = R.id.home_button

        binding.bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home_button -> {
                    navController.navigate(R.id.dashboard_nav)
                    true
                }

                R.id.transaction_button -> {
                    navController.navigate(R.id.transactions_nav)
                    true
                }

                R.id.group_button -> {
                    navController.navigate(R.id.groups_nav)
                    true
                }

                R.id.budget_button -> {
                    navController.navigate(R.id.budgets_nav)
                    true
                }

                R.id.account_button -> {
                    navController.navigate(R.id.account_nav)
                    true
                }

                else -> {
                    false
                }
            }
        }


    }

    override fun onBackPressed() {
        if (navController.currentBackStackEntry != null &&
            navController.currentBackStackEntry?.destination?.id != navController.graph.startDestinationId
        ) {
            // If the current back stack entry is not null and not at the start destination, pop the back stack.
            navController.popBackStack()
        } else {
            // If at the start destination, perform the default back action.
            if (navController.graph.id == R.id.dashboard_nav) {
                finish()
            } else {
                navController.navigate(R.id.dashboard_nav)
                binding.bottomNavigation.itemIconTintList = null
                binding.bottomNavigation.selectedItemId = R.id.home_button
            }
            super.onBackPressed()
        }
    }
}