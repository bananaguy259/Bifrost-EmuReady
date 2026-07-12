package com.solar.aurora

import androidx.annotation.DrawableRes
import com.solar.aurora.animations.LedAnimationType

enum class PresetIcon(
    val label: String,
    @DrawableRes val drawableRes: Int? = null,
    val emoji: String? = null
) {
    LIGHT("Light", drawableRes = R.drawable.baseline_lightbulb_circle_24),
    STAR("Star", drawableRes = R.drawable.ic_star),
    PLAY("Media", drawableRes = R.drawable.ic_play),
    CAMERA("Camera", drawableRes = R.drawable.ic_camera),
    COMPASS("Compass", drawableRes = R.drawable.ic_compass),
    DISPLAY("Display", drawableRes = R.drawable.ic_gallery),
    SYSTEM("System", drawableRes = R.drawable.ic_gear),
    TIMER("Timer", drawableRes = R.drawable.ic_timer);

    companion object {
        fun fromStoredName(value: String?): PresetIcon {
            return values().firstOrNull { it.name == value } ?: LIGHT
        }

        fun defaultFor(animationType: LedAnimationType): PresetIcon {
            return when (animationType) {
                LedAnimationType.STATIC,
                LedAnimationType.BREATH,
                LedAnimationType.PULSE,
                LedAnimationType.STROBE,
                LedAnimationType.CHASE,
                LedAnimationType.SPARKLE,
                LedAnimationType.RAINBOW,
                LedAnimationType.RAVE,
                LedAnimationType.FADE_TRANSITION -> LIGHT

                LedAnimationType.AUDIO_REACTIVE -> PLAY
                LedAnimationType.AMBILIGHT,
                LedAnimationType.AMBIAURORA -> DISPLAY

                LedAnimationType.BATTERY_INDICATOR -> TIMER
            }
        }
    }
}