package com.vrb.apps.themealdb.ui.base

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.vrb.apps.themealdb.R

open class BaseFragment<T : BaseViewModel> : Fragment() {

    protected lateinit var viewModel: T

    protected fun displayLoading(state: Boolean) {

        val loader = activity?.findViewById<ConstraintLayout>(R.id.baseLoader)

        loader?.let {
            if (state) {
                it.visibility = View.VISIBLE
            } else {
                it.visibility = View.GONE
            }
        }
    }
}