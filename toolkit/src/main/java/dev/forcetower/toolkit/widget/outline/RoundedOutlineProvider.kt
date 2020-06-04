package dev.forcetower.toolkit.widget.outline

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

class RoundedOutlineProvider(
    private val radius: Float
) : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(
            view.paddingLeft,
            view.paddingTop,
            view.width - view.paddingRight,
            view.height - view.paddingBottom,
            radius
        )
    }
}