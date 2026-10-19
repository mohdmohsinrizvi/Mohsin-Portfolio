package com.mohsin.portfolio.data

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemePrefs {

    private const val PREFS = "mohsin_prefs"
    private const val KEY_THEME = "theme_mode"

    const val MODE_SYSTEM = "system"
    const val MODE_DARK = "dark"
    const val MODE_LIGHT = "light"

    fun mode(context: Context): String =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY_THEME, MODE_SYSTEM) ?: MODE_SYSTEM

    fun setMode(context: Context, mode: String) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(KEY_THEME, mode).apply()
        apply(mode)
    }

    fun apply(mode: String) {
        AppCompatDelegate.setDefaultNightMode(
            when (mode) {
                MODE_DARK -> AppCompatDelegate.MODE_NIGHT_YES
                MODE_LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
    }
}
