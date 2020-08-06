package com.vrb.apps.themealdb.utils

import android.app.Activity
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.vrb.apps.themealdb.R
import retrofit2.HttpException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


fun Context.hideKeyboard() {
    val inputManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = (this as Activity).currentFocus
    if (view == null) {
        view = View(this)
    }
    inputManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showDialog(title: String, message: String) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(getString(R.string.dialog_close)) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        .create()
        .show()
}

fun Context.convertDpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        this.resources.displayMetrics
    )
}

fun Context.handleException(exception: Throwable) {
    when (exception) {

        is UnknownHostException -> {
            this.showDialog(
                getString(R.string.dialog_title_error),
                getString(R.string.dialog_error_connection)
            )
        }

        is TimeoutException -> {
            this.showDialog(
                getString(R.string.dialog_title_error),
                getString(R.string.dialog_error_timeout)
            )
        }

        is HttpException -> {
            this.showDialog(
                getString(R.string.dialog_title_error),
                getString(R.string.dialog_error_undefined)
            )
        }

        else -> {
            this.showDialog(
                getString(R.string.dialog_title_error),
                getString(R.string.dialog_error_undefined)
            )
        }
    }
}