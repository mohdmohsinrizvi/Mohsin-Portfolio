package com.mohsin.portfolio

import android.app.Application
import com.mohsin.portfolio.data.ThemePrefs

class PortfolioApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemePrefs.apply(ThemePrefs.mode(this))
    }
}
