package com.mohsin.portfolio.utils

import android.content.res.Configuration
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

fun AppCompatActivity.enableEdgeToEdge() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    val night =
        (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = !night
        isAppearanceLightNavigationBars = !night
    }
}

fun View.applySystemInsets(
    applyTop: Boolean = true,
    applyBottom: Boolean = true,
    topView: View? = null
) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.updatePadding(
            top = if (applyTop) bars.top else v.paddingTop,
            bottom = if (applyBottom) bars.bottom else v.paddingBottom
        )
        topView?.updatePadding(top = if (applyTop) bars.top else topView.paddingTop)
        insets
    }
}

fun View.children(): Sequence<View> = sequence {
    val stack = ArrayDeque<View>()
    stack.add(this@children)
    while (stack.isNotEmpty()) {
        val v = stack.removeFirst()
        yield(v)
        if (v is ViewGroup) {
            for (i in 0 until v.childCount) stack.add(v.getChildAt(i))
        }
    }
}
