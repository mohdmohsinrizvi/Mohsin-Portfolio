package com.mohsin.portfolio.ui.home

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.mohsin.portfolio.MainActivity
import com.mohsin.portfolio.R
import com.mohsin.portfolio.animations.Anim.drawLineIn
import com.mohsin.portfolio.animations.Anim.fadeUpIn
import com.mohsin.portfolio.data.PortfolioData
import com.mohsin.portfolio.databinding.FragmentHomeBinding
import com.mohsin.portfolio.ui.settings.SettingsBottomSheet
import com.mohsin.portfolio.utils.children

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        populate()
        wireActions()
        setupCollapsingHeader()
        runEntrance()
        applyInsets()
    }

    private fun applyInsets() {
        val navBar = resources.getDimensionPixelSize(R.dimen.nav_bar_height)
        ViewCompat.setOnApplyWindowInsetsListener(binding.scrollView) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top, bottom = bars.bottom + navBar)
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.compactHeader) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top)
            insets
        }
    }

    private fun populate() {
        val p = PortfolioData.profile
        binding.heroName.text = p.fullName
        binding.heroHeadline.text = p.headline
        binding.heroIntro.text = p.intro

        p.descriptors.forEach { d ->
            val tv = TextView(requireContext()).apply {
                text = d
                setTextAppearance(R.style.TextAppearance_Mohsin_MonoPrimary)
                val pad = (6 * resources.displayMetrics.density).toInt()
                setPadding(0, pad, 0, pad)
            }
            binding.descriptorList.addView(tv)
        }

        binding.section01.sectionNumber.text = "01 — THE INTRODUCTION"
        binding.section01.sectionTitle.text = "A short profile"
        binding.introBody.text = p.intro

        binding.section02.sectionNumber.text = "02 — WHAT I CREATE"
        binding.section02.sectionTitle.text = "Craft & focus"
        PortfolioData.whatICreate.forEach { item ->
            binding.whatCreateList.addView(buildDotRow(item))
        }

        binding.section03.sectionNumber.text = "03 — SELECTED WORK"
        binding.section03.sectionTitle.text = "Recent projects"
        PortfolioData.projects.take(2).forEach { project ->
            val row = buildProjectRow(project.title, project.category, project.year)
            row.setOnClickListener { (activity as MainActivity).openProject(project.id) }
            binding.workPreviewList.addView(row)
        }

        binding.section04.sectionNumber.text = "04 — MY JOURNEY"
        binding.section04.sectionTitle.text = "Experience & education"
        val exp = PortfolioData.experiences.first()
        val edu = PortfolioData.education
        binding.journeyPreview.text =
            "${exp.role} · ${exp.organization} · ${exp.duration}\n" +
                "${edu.degree} · ${edu.institution}"

        binding.section05.sectionNumber.text = "05 — BEYOND THE CODE"
        binding.section05.sectionTitle.text = "Interests"
        binding.beyondBody.text =
            "Entrepreneurship, creative problem-solving, design, and continuous learning shape how Mohsin approaches technology."
        PortfolioData.beyondTheCode.forEach { chip ->
            binding.beyondChips.addView(buildChip(chip))
        }

        binding.section06.sectionNumber.text = "06 — LET’S BUILD"
        binding.section06.sectionTitle.text = "Something meaningful"
        binding.ctaBody.text =
            "Open to meaningful opportunities, collaborations, and conversations around technology, design, and innovation."
    }

    private fun wireActions() {
        binding.ctaWork.setOnClickListener { tabTo(MainActivity.TAG_WORK) }
        binding.ctaConnect.setOnClickListener { tabTo(MainActivity.TAG_CONNECT) }
        binding.ctaFinal.setOnClickListener { tabTo(MainActivity.TAG_CONNECT) }
        binding.introLink.setOnClickListener { (activity as MainActivity).openAbout() }
        binding.workViewAll.setOnClickListener { tabTo(MainActivity.TAG_WORK) }
        binding.journeyLink.setOnClickListener { tabTo(MainActivity.TAG_JOURNEY) }
        binding.compactAction.setOnClickListener {
            SettingsBottomSheet().show(childFragmentManager, "settings")
        }
    }

    private fun tabTo(tag: String) {
        val main = activity as MainActivity
        when (tag) {
            MainActivity.TAG_WORK -> main.onTabPublic(tag)
            MainActivity.TAG_JOURNEY -> main.onTabPublic(tag)
            MainActivity.TAG_CONNECT -> main.onTabPublic(tag)
        }
    }

    private fun setupCollapsingHeader() {
        val max = (220 * resources.displayMetrics.density).toInt()
        binding.scrollView.setOnScrollChangeListener { _: View?, _: Int, scrollY: Int, _: Int, _: Int ->
            val t = (scrollY.toFloat() / max).coerceIn(0f, 1f)
            binding.compactHeader.alpha = t
            binding.compactHeader.visibility = if (scrollY > 8) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun runEntrance() {
        val hero = listOf(
            binding.heroLocation, binding.heroName, binding.heroHeadline,
            binding.descriptorList, binding.heroIntro
        )
        hero.forEachIndexed { i, v -> v.fadeUpIn(startDelayMs = 60L * i) }

        applyHeroGradient()

        val sections = listOf(
            binding.section01, binding.section02, binding.section03,
            binding.section04, binding.section05, binding.section06
        )
        sections.forEachIndexed { i, s ->
            val delay = 360L + 90L * i
            val header = s.root
            val target = (header.parent as? View)?.takeIf { it !== binding.content && it !== binding.root } ?: header
            target.fadeUpIn(startDelayMs = delay)
            (header.getChildAt(2) as? View)?.drawLineIn(
                horizontal = true, durationMs = 700, startDelayMs = delay + 120
            )
        }
    }

    private fun applyHeroGradient() {
        binding.heroName.post {
            val w = binding.heroName.width
            if (w <= 0) return@post
            binding.heroName.paint.shader = android.graphics.LinearGradient(
                0f, 0f, w.toFloat(), 0f,
                intArrayOf(
                    requireContext().getColor(R.color.colorTextPrimary),
                    requireContext().getColor(R.color.colorAccentGold),
                    requireContext().getColor(R.color.colorTextPrimary)
                ),
                floatArrayOf(0f, 0.55f, 1f),
                android.graphics.Shader.TileMode.CLAMP
            )
            binding.heroName.invalidate()
        }
    }

    private fun buildDotRow(text: String): View {
        val row = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            val padV = (8 * resources.displayMetrics.density).toInt()
            setPadding(0, padV, 0, padV)
        }
        val dot = View(requireContext()).apply {
            val size = (5 * resources.displayMetrics.density).toInt()
            layoutParams = LinearLayout.LayoutParams(size, size).apply {
                marginEnd = (14 * resources.displayMetrics.density).toInt()
            }
            setBackgroundResource(R.drawable.bg_badge)
        }
        val tv = TextView(requireContext()).apply {
            this.text = text
            setTextAppearance(R.style.TextAppearance_Mohsin_Body)
            layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        }
        row.addView(dot)
        row.addView(tv)
        return row
    }

    private fun buildProjectRow(title: String, category: String, year: String): View {
        val row = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            val pad = (12 * resources.displayMetrics.density).toInt()
            setPadding(pad, pad, pad, pad)
            setBackgroundResource(R.drawable.bg_chip)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = (10 * resources.displayMetrics.density).toInt() }
        }
        val cat = TextView(requireContext()).apply {
            text = "$category · $year"
            setTextAppearance(R.style.TextAppearance_Mohsin_Mono)
        }
        val t = TextView(requireContext()).apply {
            text = title
            setTextAppearance(R.style.TextAppearance_Mohsin_SectionTitle)
            setPadding(0, (6 * resources.displayMetrics.density).toInt(), 0, 0)
        }
        row.addView(cat)
        row.addView(t)
        return row
    }

    private fun buildChip(text: String): TextView {
        return TextView(requireContext()).apply {
            this.text = text
            setTextAppearance(R.style.TextAppearance_Mohsin_Body)
            val hPad = (14 * resources.displayMetrics.density).toInt()
            val vPad = (10 * resources.displayMetrics.density).toInt()
            setPadding(hPad, vPad, hPad, vPad)
            setBackgroundResource(R.drawable.bg_chip)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = (8 * resources.displayMetrics.density).toInt() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
