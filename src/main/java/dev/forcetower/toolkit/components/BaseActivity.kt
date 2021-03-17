package dev.forcetower.toolkit.components

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity() {
    open fun showSnack(string: String, duration: Int = Snackbar.LENGTH_SHORT) {
        getSnackInstance(string, duration)?.show()
    }
    open fun getSnackInstance(string: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar? = null
}