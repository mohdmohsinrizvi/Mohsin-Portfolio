package com.mohsin.portfolio

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.mohsin.portfolio.animations.Anim.fadeIn
import com.mohsin.portfolio.animations.Anim.fadeOutThen
import com.mohsin.portfolio.animations.Anim.reducedMotion
import com.mohsin.portfolio.animations.Anim.drawLineIn
import com.mohsin.portfolio.databinding.ActivityLaunchBinding
import com.mohsin.portfolio.utils.enableEdgeToEdge

class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding
    private var navigated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playIntro()
    }

    private fun playIntro() {
        if (reducedMotion()) {
            goHome()
            return
        }
        binding.monogram.alpha = 0f
        binding.monogram.scaleX = 0.92f
        binding.monogram.scaleY = 0.92f
        binding.wordmark.alpha = 0f
        binding.tagline.alpha = 0f
        binding.hairline.drawLineIn(horizontal = true, durationMs = 600)

        binding.monogram.animate()
            .alpha(1f).scaleX(1f).scaleY(1f)
            .setStartDelay(120).setDuration(480)
            .setInterpolator(android.view.animation.DecelerateInterpolator(2f))
            .start()

        binding.wordmark.fadeIn(startDelayMs = 420, durationMs = 400)
        binding.tagline.fadeIn(startDelayMs = 620, durationMs = 400)

        binding.root.postDelayed({ goHome() }, 1500)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN && !navigated) {
            goHome()
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun goHome() {
        if (navigated) return
        navigated = true
        binding.root.fadeOutThen({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, durationMs = 220)
    }
}
