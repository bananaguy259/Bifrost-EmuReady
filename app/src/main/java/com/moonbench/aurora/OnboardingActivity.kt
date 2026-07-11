package com.moonbench.aurora

import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class OnboardingActivity : AppCompatActivity() {

    private data class OnboardPage(
        val iconRes: Int,
        val title: String,
        val description: String
    )

    private val pages = listOf(
        OnboardPage(
            iconRes = R.mipmap.ic_launcher_foreground,
            title = "Welcome to Aurora",
            description = "Turn your handheld's joystick LEDs into part of the experience — synced to your screen, your audio, or however you like it."
        ),
        OnboardPage(
            iconRes = R.drawable.ic_onboard_screen,
            title = "Lights That Match Your Screen",
            description = "Ambilight mode samples your screen in real time, so your controller glows with the colors of whatever you're playing."
        ),
        OnboardPage(
            iconRes = R.drawable.ic_onboard_soundwave,
            title = "Lights That Move To Sound",
            description = "Audio Reactive mode pulses your LEDs to your game's audio, music, or anything else playing on your device."
        ),
        OnboardPage(
            iconRes = R.drawable.ic_onboard_apps,
            title = "One Preset Per Game",
            description = "Save as many presets as you like, then let Aurora switch between them automatically based on whichever app is open."
        ),
        OnboardPage(
            iconRes = R.drawable.ic_onboard_widget,
            title = "Control It From Anywhere",
            description = "Start or stop instantly from a home screen widget — no need to open the app to change what's running."
        )
    )

    private var currentIndex = 0

    private lateinit var iconView: ImageView
    private lateinit var titleView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var dotsContainer: LinearLayout
    private lateinit var nextButton: MaterialButton
    private lateinit var skipButton: TextView

    private lateinit var contentGroup: android.view.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        contentGroup = findViewById(R.id.onboardContentGroup)
        iconView = findViewById(R.id.onboardIcon)
        titleView = findViewById(R.id.onboardTitle)
        descriptionView = findViewById(R.id.onboardDescription)
        dotsContainer = findViewById(R.id.onboardDotsContainer)
        nextButton = findViewById(R.id.onboardNextButton)
        skipButton = findViewById(R.id.onboardSkipButton)

        buildDots()
        renderPage(currentIndex, animate = false)

        nextButton.setOnClickListener {
            if (currentIndex < pages.lastIndex) {
                currentIndex++
                renderPage(currentIndex, animate = true)
            } else {
                finishOnboarding()
            }
        }

        skipButton.setOnClickListener {
            finishOnboarding()
        }
    }

    private fun buildDots() {
        dotsContainer.removeAllViews()
        val dotHeight = (6 * resources.displayMetrics.density).toInt()
        val inactiveWidth = dotHeight
        val activeWidth = (20 * resources.displayMetrics.density).toInt()
        val margin = (4 * resources.displayMetrics.density).toInt()

        for (i in pages.indices) {
            val dot = android.view.View(this)
            val params = LinearLayout.LayoutParams(
                if (i == currentIndex) activeWidth else inactiveWidth,
                dotHeight
            )
            params.marginStart = margin
            params.marginEnd = margin
            dot.layoutParams = params
            dot.setBackgroundResource(
                if (i == currentIndex) R.drawable.onboard_dot_active else R.drawable.onboard_dot_inactive
            )
            dotsContainer.addView(dot)
        }
    }

    private fun updateDots() {
        val activeWidth = (20 * resources.displayMetrics.density).toInt()
        val inactiveWidth = (6 * resources.displayMetrics.density).toInt()
        val dotHeight = inactiveWidth

        for (i in 0 until dotsContainer.childCount) {
            val dot = dotsContainer.getChildAt(i)
            val isActive = i == currentIndex
            val params = dot.layoutParams as LinearLayout.LayoutParams
            params.width = if (isActive) activeWidth else inactiveWidth
            params.height = dotHeight
            dot.layoutParams = params
            dot.setBackgroundResource(
                if (isActive) R.drawable.onboard_dot_active else R.drawable.onboard_dot_inactive
            )
        }
    }

    private fun renderPage(index: Int, animate: Boolean) {
        val page = pages[index]

        fun applyContent() {
            iconView.setImageResource(page.iconRes)
            titleView.text = page.title
            descriptionView.text = page.description
            nextButton.text = if (index == pages.lastIndex) "Get Started" else "Next"
            updateDots()
        }

        if (!animate) {
            applyContent()
            return
        }

        contentGroup.animate()
            .alpha(0f)
            .setDuration(120)
            .setInterpolator(AccelerateInterpolator())
            .withEndAction {
                applyContent()
                contentGroup.animate()
                    .alpha(1f)
                    .setDuration(180)
                    .setInterpolator(DecelerateInterpolator())
                    .start()
            }
            .start()
    }

    private fun finishOnboarding() {
        val prefs = getSharedPreferences("aurora_prefs", MODE_PRIVATE)
        prefs.edit().putBoolean(MainActivity.PREF_ONBOARDING_SHOWN, true).apply()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
