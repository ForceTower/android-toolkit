package dev.forcetower.toolkit.components

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

abstract class BaseFragment : Fragment() {
    fun showSnack(string: String, duration: Int = Snackbar.LENGTH_SHORT) {
        getSnack(string, duration)?.show()
    }

    fun getSnack(string: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar? {
        val activity = requireActivity()
        return if (activity is BaseActivity) {
            activity.getSnackInstance(string, duration)
        } else {
            Timber.i("Not part of BaseActivity")
            null
        }
    }
}
