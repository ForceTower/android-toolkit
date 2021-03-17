package dev.forcetower.toolkit.bindings

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import dev.forcetower.toolkit.extensions.doOnApplyWindowInsets
import dev.forcetower.toolkit.extensions.doOnApplyWindowMarginInsets
import dev.forcetower.toolkit.extensions.getPixelsFromDp
import dev.forcetower.toolkit.widget.outline.CircularOutlineProvider
import dev.forcetower.toolkit.widget.outline.RoundedOutlineProvider
import timber.log.Timber

@BindingAdapter(
    "paddingStartSystemWindowInsets",
    "paddingTopSystemWindowInsets",
    "paddingEndSystemWindowInsets",
    "paddingBottomSystemWindowInsets",
    "consumeSystemWindowInsets",
    requireAll = false
)
fun applySystemWindows(
    view: View,
    applyLeft: Boolean,
    applyTop: Boolean,
    applyRight: Boolean,
    applyBottom: Boolean,
    consumeInsets: Boolean = false
) {
    view.doOnApplyWindowInsets { _, allInsets, padding ->
        val insets = allInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        val left = if (applyLeft) insets.left else 0
        val top = if (applyTop) insets.top else 0
        val right = if (applyRight) insets.right else 0
        val bottom = if (applyBottom) insets.bottom else 0

        view.setPadding(
            padding.left + left,
            padding.top + top,
            padding.right + right,
            padding.bottom + bottom
        )

        if (consumeInsets) {
            WindowInsetsCompat.CONSUMED
        }
    }
}

@BindingAdapter(
    "marginStartSystemWindowInsets",
    "marginTopSystemWindowInsets",
    "marginEndSystemWindowInsets",
    "marginBottomSystemWindowInsets",
    requireAll = false
)
fun applyMarginSystemWindows(
    view: View,
    applyLeft: Boolean,
    applyTop: Boolean,
    applyRight: Boolean,
    applyBottom: Boolean
) {
    view.doOnApplyWindowMarginInsets { _, allInsets, margins ->
        val insets = allInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        val left = if (applyLeft) insets.left else 0
        val top = if (applyTop) insets.top else 0
        val right = if (applyRight) insets.right else 0
        val bottom = if (applyBottom) insets.bottom else 0

        (view.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
            setMargins(
                margins.left + left,
                margins.top + top,
                margins.right + right,
                margins.bottom + bottom
            )
        }?.also {
            view.layoutParams = it
        }
    }
}

@BindingAdapter("roundedViewRadius")
fun roundedViewRadius(view: View, radius: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        view.clipToOutline = true
        view.outlineProvider = RoundedOutlineProvider(view.context.getPixelsFromDp(radius))
    }
}

@BindingAdapter("clipToCircle")
fun clipToCircle(view: View, clip: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        view.clipToOutline = clip
        view.outlineProvider = if (clip) CircularOutlineProvider else null
    }
}

@BindingAdapter("goneIf")
fun goneIf(view: View, gone: Boolean) {
    view.visibility = if (gone) View.GONE else View.VISIBLE
}

@BindingAdapter("goneUnless")
fun goneUnless(view: View, condition: Boolean) {
    view.visibility = if (condition) View.VISIBLE else View.GONE
}

@BindingAdapter("invisibleUnless")
fun invisibleUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("swipeRefreshColors")
fun setSwipeRefreshColors(swipeRefreshLayout: SwipeRefreshLayout, colorResIds: IntArray) {
    swipeRefreshLayout.setColorSchemeColors(*colorResIds)
}

@BindingAdapter("refreshing")
fun swipeRefreshing(refreshLayout: SwipeRefreshLayout, refreshing: Boolean) {
    refreshLayout.isRefreshing = refreshing
}

@BindingAdapter("onSwipeRefresh")
fun onSwipeRefresh(view: SwipeRefreshLayout, function: SwipeRefreshLayout.OnRefreshListener) {
    view.setOnRefreshListener(function)
}

@BindingAdapter("swipeEnabled")
fun swipeEnabled(view: SwipeRefreshLayout, enabled: Boolean) {
    view.isEnabled = enabled
}
