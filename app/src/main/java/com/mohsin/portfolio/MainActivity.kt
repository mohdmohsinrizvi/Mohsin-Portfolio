package com.mohsin.portfolio

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mohsin.portfolio.databinding.ActivityMainBinding
import com.mohsin.portfolio.ui.about.AboutFragment
import com.mohsin.portfolio.ui.connect.ConnectFragment
import com.mohsin.portfolio.ui.home.HomeFragment
import com.mohsin.portfolio.ui.journey.JourneyFragment
import com.mohsin.portfolio.ui.resume.ResumeFragment
import com.mohsin.portfolio.ui.work.ProjectDetailFragment
import com.mohsin.portfolio.ui.work.WorkFragment
import com.mohsin.portfolio.utils.enableEdgeToEdge

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentRootTag: String = TAG_HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNav.root) { v, insets ->
            v.updatePadding(bottom = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom)
            insets
        }

        setupBottomNav()

        supportFragmentManager.addOnBackStackChangedListener {
            val detail = supportFragmentManager.backStackEntryCount > 0
            binding.bottomNav.root.visibility = if (detail) android.view.View.GONE else android.view.View.VISIBLE
        }

        if (savedInstanceState == null) {
            showRoot(HomeFragment(), TAG_HOME)
        }
    }

    private fun setupBottomNav() {
        binding.bottomNav.navHome.setOnClickListener { onTab(TAG_HOME) }
        binding.bottomNav.navWork.setOnClickListener { onTab(TAG_WORK) }
        binding.bottomNav.navJourney.setOnClickListener { onTab(TAG_JOURNEY) }
        binding.bottomNav.navConnect.setOnClickListener { onTab(TAG_CONNECT) }
        selectTab(TAG_HOME)
    }

    private fun onTab(tag: String) {
        if (tag == currentRootTag && supportFragmentManager.backStackEntryCount == 0) return
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        when (tag) {
            TAG_HOME -> showRoot(HomeFragment(), tag)
            TAG_WORK -> showRoot(WorkFragment(), tag)
            TAG_JOURNEY -> showRoot(JourneyFragment(), tag)
            TAG_CONNECT -> showRoot(ConnectFragment(), tag)
        }
    }

    private fun selectTab(tag: String) {
        currentRootTag = tag
        val active = getColor(R.color.colorNavActive)
        val inactive = getColor(R.color.colorNavInactive)
        data class Tab(val view: android.view.View, val icon: ImageView, val label: TextView)
        val tabs = listOf(
            Tab(binding.bottomNav.navHome, binding.bottomNav.navHomeIcon, binding.bottomNav.navHomeLabel) to (tag == TAG_HOME),
            Tab(binding.bottomNav.navWork, binding.bottomNav.navWorkIcon, binding.bottomNav.navWorkLabel) to (tag == TAG_WORK),
            Tab(binding.bottomNav.navJourney, binding.bottomNav.navJourneyIcon, binding.bottomNav.navJourneyLabel) to (tag == TAG_JOURNEY),
            Tab(binding.bottomNav.navConnect, binding.bottomNav.navConnectIcon, binding.bottomNav.navConnectLabel) to (tag == TAG_CONNECT)
        )
        tabs.forEach { (tab, selected) ->
            tab.view.isSelected = selected
            val color = if (selected) active else inactive
            tab.icon.setColorFilter(color)
            tab.label.setTextColor(color)
            tab.label.textSize = if (selected) 11.5f else 10.5f
        }
    }

    private fun showRoot(fragment: Fragment, tag: String) {
        selectTab(tag)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_enter,
                R.anim.fragment_exit,
                R.anim.fragment_pop_enter,
                R.anim.fragment_pop_exit
            )
            .replace(R.id.fragmentContainer, fragment, tag)
            .commit()
    }

    fun openDetail(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_enter,
                R.anim.fragment_exit,
                R.anim.fragment_pop_enter,
                R.anim.fragment_pop_exit
            )
            .replace(R.id.fragmentContainer, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    fun onTabPublic(tag: String) = onTab(tag)

    fun openProject(projectId: String) {
        openDetail(ProjectDetailFragment.newInstance(projectId), "project_$projectId")
    }

    fun openAbout() = openDetail(AboutFragment(), TAG_ABOUT)

    fun openResume() = openDetail(ResumeFragment(), TAG_RESUME)

    fun openJourney() {
        onTab(TAG_JOURNEY)
    }

    companion object {
        const val TAG_HOME = "home"
        const val TAG_WORK = "work"
        const val TAG_JOURNEY = "journey"
        const val TAG_CONNECT = "connect"
        const val TAG_ABOUT = "about"
        const val TAG_RESUME = "resume"
    }
}
