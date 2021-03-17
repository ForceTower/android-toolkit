package dev.forcetower.toolkit.bindings

import android.content.res.ColorStateList
import androidx.annotation.StringRes
import androidx.core.view.children
import androidx.core.view.get
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dev.forcetower.toolkit.R
import dev.forcetower.toolkit.extensions.resolveColorAttr

@BindingAdapter("textInputError")
fun textInputError(input: TextInputEditText, @StringRes error: Int?) {
    if (error == null) {
        input.error = null
    } else {
        input.error = input.context.getString(error)
        input.requestFocus()
    }
}

@BindingAdapter("textHelperError")
fun textHelperError(layout: TextInputLayout, @StringRes error: Int?) {
    if (error == null) {
        layout.helperText = null
    } else {
        layout.helperText = layout.context.getString(error)
        layout[0].requestFocus()
    }
    layout.setHelperTextColor(ColorStateList.valueOf(layout.context.resolveColorAttr(R.attr.colorError)))
}