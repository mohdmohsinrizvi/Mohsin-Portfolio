package com.mohsin.portfolio.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.TextView

object Anim {

    fun reducedMotion(): Boolean = !ValueAnimator.areAnimatorsEnabled()

    fun View.fadeUpIn(startDelayMs: Long = 0, durationMs: Long = 420) {
        if (reducedMotion()) {
            alpha = 1f
            translationY = 0f
            return
        }
        alpha = 0f
        translationY = 24f
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .translationY(0f)
            .setStartDelay(startDelayMs)
            .setDuration(durationMs)
            .setInterpolator(DecelerateInterpolator(2f))
            .start()
    }

    fun View.fadeIn(startDelayMs: Long = 0, durationMs: Long = 320) {
        if (reducedMotion()) {
            alpha = 1f
            visibility = View.VISIBLE
            return
        }
        alpha = 0f
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .setStartDelay(startDelayMs)
            .setDuration(durationMs)
            .setInterpolator(DecelerateInterpolator(1.5f))
            .start()
    }

    fun View.fadeOutThen(onEnd: () -> Unit, durationMs: Long = 300) {
        if (reducedMotion()) {
            onEnd()
            return
        }
        animate()
            .alpha(0f)
            .setDuration(durationMs)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    onEnd()
                }
            })
            .start()
    }

    fun TextView.countUpTo(
        target: Int,
        suffix: String = "",
        durationMs: Long = 900,
        startDelayMs: Long = 0
    ) {
        if (reducedMotion()) {
            text = "$target$suffix"
            return
        }
        text = "0$suffix"
        val animator = ValueAnimator.ofInt(0, target)
        animator.duration = durationMs
        animator.startDelay = startDelayMs
        animator.interpolator = DecelerateInterpolator(2f)
        animator.addUpdateListener {
            text = "${it.animatedValue as Int}$suffix"
        }
        animator.start()
    }

    fun View.drawLineIn(horizontal: Boolean = false, durationMs: Long = 500, startDelayMs: Long = 0) {
        if (reducedMotion()) return
        if (horizontal) {
            scaleX = 0f
            animate().scaleX(1f).setDuration(durationMs).setStartDelay(startDelayMs)
                .setInterpolator(DecelerateInterpolator(2f)).start()
        } else {
            scaleY = 0f
            animate().scaleY(1f).setDuration(durationMs).setStartDelay(startDelayMs)
                .setInterpolator(DecelerateInterpolator(2f)).start()
        }
    }

    fun android.view.ViewGroup.staggerChildren(baseDelayMs: Long = 0, stepMs: Long = 60) {
        for (i in 0 until childCount) {
            getChildAt(i).fadeUpIn(startDelayMs = baseDelayMs + stepMs * i)
        }
    }
}
