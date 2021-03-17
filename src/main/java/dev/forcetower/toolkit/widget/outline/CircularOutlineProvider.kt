package dev.forcetower.toolkit.widget.outline

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi

@RequiresApi(21)
object CircularOutlineProvider : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        outline.setOval(
            view.paddingLeft,
            view.paddingTop,
            view.width - view.paddingRight,
            view.height - view.paddingBottom
        )
    }
}