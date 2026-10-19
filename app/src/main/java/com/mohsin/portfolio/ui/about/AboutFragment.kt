package com.mohsin.portfolio.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.mohsin.portfolio.R
import com.mohsin.portfolio.animations.Anim.staggerChildren
import com.mohsin.portfolio.data.PortfolioData
import com.mohsin.portfolio.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    private val density get() = requireContext().resources.displayMetrics.density

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.topBar.topBarTitle.text = "About"
        binding.topBar.backButton.setOnClickListener { parentFragmentManager.popBackStack() }

        val p = PortfolioData.profile
        binding.aboutName.text = p.fullName

        val meta = listOf(
            "LOCATION" to p.location,
            "EDUCATION" to "B.Tech, Computer Science and Engineering",
            "INSTITUTION" to "Integral University, Lucknow",
            "FOCUS" to "Front-End Web Development",
            "EXPERIENCE" to "Freelance Web Developer since August 2024",
            "ROLE" to "Campus Ambassador, Entrepreneurship Cell, IIT Bombay"
        )
        meta.forEach { (label, value) ->
            val row = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = matchParentWrap().apply { topMargin = dp(14) }
            }
            row.addView(TextView(requireContext()).apply {
                text = label
                setTextAppearance(R.style.TextAppearance_Mohsin_Mono)
                layoutParams = LinearLayout.LayoutParams(dp(110), ViewGroup.LayoutParams.WRAP_CONTENT)
            })
            row.addView(TextView(requireContext()).apply {
                text = value
                setTextAppearance(R.style.TextAppearance_Mohsin_Body)
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            })
            binding.metaList.addView(row)
        }

        p.bioParagraphs.forEach { para ->
            binding.bioList.addView(TextView(requireContext()).apply {
                text = para
                setTextAppearance(R.style.TextAppearance_Mohsin_BodySecondary)
                setPadding(0, dp(12), 0, dp(12))
            })
        }

        p.philosophy.forEachIndexed { i, principle ->
            binding.principleList.addView(TextView(requireContext()).apply {
                text = String.format("%02d   %s", i + 1, principle)
                setTextAppearance(R.style.TextAppearance_Mohsin_Body)
                setPadding(0, dp(10), 0, dp(10))
            })
        }

        (binding.scrollView.getChildAt(0) as? ViewGroup)?.staggerChildren(stepMs = 55)

        applyInsets()
    }

    private fun matchParentWrap() = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )

    private fun dp(v: Int) = (v * density).toInt()

    private fun applyInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.scrollView) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top, bottom = bars.bottom)
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.topBar.topBar) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top)
            insets
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
