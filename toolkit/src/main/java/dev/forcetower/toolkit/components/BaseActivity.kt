package dev.forcetower.toolkit.components

import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {
    open fun showSnack(string: String, duration: Int = Snackbar.LENGTH_SHORT) {
        getSnackInstance(string, duration)?.show()
    }
    open fun getSnackInstance(string: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar? = null
}