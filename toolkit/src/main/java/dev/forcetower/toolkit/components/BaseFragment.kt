package dev.forcetower.toolkit.components

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dev.forcetower.toolkit.extensions.doOnApplyWindowInsets
import timber.log.Timber

abstract class BaseFragment : Fragment() {
    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (shouldApplyInsets()) {
            view.doOnApplyWindowInsets { v, allInsets, padding ->
                val insets = allInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.updatePadding(
                    bottom = padding.bottom + insets.bottom,
                    top = padding.top + insets.top,
                    left = padding.left + insets.left,
                    right = padding.right + insets.right
                )
            }
        }
    }

    fun showSnack(string: String, duration: Int = Snackbar.LENGTH_SHORT) {
        getSnack(string, duration)?.show()
    }

    fun getSnack(string: String, duration: Int = Snackbar.LENGTH_SHORT): Snackbar? {
        val activity = activity
        return if (activity is BaseActivity) {
            activity.getSnackInstance(string, duration)
        } else {
            Timber.i("Not part of BaseActivity")
            null
        }
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
    }

    open fun shouldApplyInsets() = false
}