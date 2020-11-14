package dev.forcetower.toolkit.components

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import androidx.annotation.CallSuper
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

abstract class BaseSheetDialogFragment : BottomSheetDialogFragment() {
    @CallSuper
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val sheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        try {
            sheetDialog.setOnShowListener {
                val bottomSheet = sheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)!!
                val behavior = BottomSheetBehavior.from(bottomSheet)
                behavior.skipCollapsed = true
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        } catch (t: Throwable) {
            Timber.d(t, "Hum...")
        }
        return sheetDialog
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
}