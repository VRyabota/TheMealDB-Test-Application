package com.vrb.apps.themealdb.ui

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import com.vrb.apps.themealdb.R
import com.vrb.apps.themealdb.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavHost {

    private var backPressedOnce = false

    private val navigationController by lazy { findNavController(R.id.base_nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun getNavController() = navigationController

    override fun onSupportNavigateUp() = navigationController.navigateUp()

    override fun onBackPressed() {
        if (base_nav_host_fragment.childFragmentManager.backStackEntryCount < 1) {
            if (backPressedOnce) {
                super.onBackPressed()
            } else {
                backPressedOnce = true
                this.showToast(getString(R.string.app_text_exit))

                Handler().postDelayed({ backPressedOnce = false }, 2000)
            }
        } else {
            super.onBackPressed()
        }
    }
}