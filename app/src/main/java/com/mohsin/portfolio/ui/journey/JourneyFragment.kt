package com.mohsin.portfolio.ui.journey

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
import com.mohsin.portfolio.R
import com.mohsin.portfolio.animations.Anim.countUpTo
import com.mohsin.portfolio.animations.Anim.fadeUpIn
import com.mohsin.portfolio.data.PortfolioData
import com.mohsin.portfolio.databinding.FragmentJourneyBinding
import com.mohsin.portfolio.models.Certification
import com.mohsin.portfolio.models.Experience

class JourneyFragment : Fragment() {

    private var _binding: FragmentJourneyBinding? = null
    private val binding get() = _binding!!
    private val density get() = requireContext().resources.displayMetrics.density
    private val metricAnims = mutableListOf<MetricAnim>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJourneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.topBar.topBarTitle.text = "Journey"
        binding.topBar.backButton.visibility = View.GONE

        val root = binding.timelineContent
        root.removeAllViews()

        root.addView(chapter("CAREER TIMELINE"))
        PortfolioData.experiences.forEach { exp -> root.addView(buildExperienceCard(exp)) }

        root.addView(spacer(16))
        root.addView(chapter("ACADEMIC JOURNEY"))
        root.addView(buildEducation())

        root.addView(chapter("EXPERTISE"))
        root.addView(buildExpertise())

        root.addView(chapter("DIGITAL CREDENTIALS"))
        root.addView(sectionNote("Certifications, virtual simulations, and programmes — clearly labelled by type."))
        PortfolioData.certifications.forEach { cert -> root.addView(buildCredentialCard(cert)) }

        root.addView(chapter("THE IMPACT COLLECTION"))
        root.addView(sectionNote("Highlights from Mohsin’s résumé, presented with context."))
        root.addView(buildImpact())

        for (i in 0 until root.childCount) {
            root.getChildAt(i).fadeUpIn(startDelayMs = 30L * i)
        }

        metricAnims.forEachIndexed { i, anim ->
            anim.view.countUpTo(
                target = anim.target,
                suffix = anim.suffix,
                startDelayMs = 700L + 70L * i
            )
        }

        applyInsets()
    }

    private fun chapter(title: String): View {
        val wrap = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = matchParentWrap().apply { topMargin = dp(36) }
        }
        wrap.addView(View(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(1))
            setBackgroundColor(getColorCompat(R.color.colorDivider))
        })
        wrap.addView(TextView(requireContext()).apply {
            text = title
            setTextAppearance(R.style.TextAppearance_Mohsin_MonoAccent)
            setPadding(0, dp(14), 0, dp(4))
        })
        return wrap
    }

    private fun buildExperienceCard(exp: Experience): View {
        val card = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = matchParentWrap().apply { topMargin = dp(16) }
        }
        val rail = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(dp(24), ViewGroup.LayoutParams.WRAP_CONTENT)
            gravity = Gravity.CENTER_HORIZONTAL
        }
        rail.addView(View(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(dp(10), dp(10))
            setBackgroundResource(R.drawable.bg_badge)
        })
        rail.addView(View(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(dp(1), 0, 1f).apply {
                topMargin = dp(6); bottomMargin = dp(6)
            }
            setBackgroundColor(getColorCompat(R.color.colorDivider))
        })
        val content = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        }
        content.addView(TextView(requireContext()).apply {
            text = exp.duration.uppercase()
            setTextAppearance(R.style.TextAppearance_Mohsin_MonoAccent)
        })
        content.addView(TextView(requireContext()).apply {
            text = exp.role
            setTextAppearance(R.style.TextAppearance_Mohsin_H2)
            setPadding(0, dp(6), 0, 0)
        })
        content.addView(TextView(requireContext()).apply {
            text = buildString {
                append(exp.organization)
                exp.organizationDetail?.let { append(" · $it") }
                append("\n")
                append(exp.location)
            }
            setTextAppearance(R.style.TextAppearance_Mohsin_BodySecondary)
            setPadding(0, dp(4), 0, 0)
        })
        exp.bullets.forEach { bullet ->
            content.addView(TextView(requireContext()).apply {
                text = "—  $bullet"
                setTextAppearance(R.style.TextAppearance_Mohsin_BodySmall)
                setPadding(0, dp(10), 0, 0)
            })
        }
        card.addView(rail)
        card.addView(content)
        return card
    }

    private fun buildEducation(): View {
        val wrap = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = matchParentWrap()
        }
        val edu = PortfolioData.education
        wrap.addView(TextView(requireContext()).apply {
            text = edu.degree
            setTextAppearance(R.style.TextAppearance_Mohsin_DisplaySmall)
            setPadding(0, dp(8), 0, 0)
        })
        wrap.addView(TextView(requireContext()).apply {
            text = "${edu.institution}\n${edu.location}\n${edu.duration}"
            setTextAppearance(R.style.TextAppearance_Mohsin_BodySecondary)
            setPadding(0, dp(10), 0, 0)
        })
        wrap.addView(sectionNote("RELEVANT COURSEWORK").apply {
            setPadding(0, dp(18), 0, dp(4))
        })
        wrap.addView(chipFlow(edu.coursework))
        return wrap
    }

    private fun buildExpertise(): View {
        val wrap = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = matchParentWrap()
        }
        PortfolioData.skillGroups.forEach { group ->
            wrap.addView(TextView(requireContext()).apply {
                text = group.title
                setTextAppearance(R.style.TextAppearance_Mohsin_SectionTitle)
                setPadding(0, dp(16), 0, 0)
            })
            wrap.addView(chipFlow(group.skills))
        }
        return wrap
    }

    private fun buildCredentialCard(cert: Certification): View {
        val card = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = matchParentWrap().apply { topMargin = dp(14) }
            setBackgroundResource(R.drawable.bg_card)
            val pad = dp(16)
            setPadding(pad, pad, pad, pad)
        }
        card.addView(TextView(requireContext()).apply {
            text = cert.type.badge
            setTextAppearance(R.style.TextAppearance_Mohsin_MonoAccent)
        })
        card.addView(TextView(requireContext()).apply {
            text = cert.title
            setTextAppearance(R.style.TextAppearance_Mohsin_SectionTitle)
            setPadding(0, dp(8), 0, 0)
        })
        card.addView(TextView(requireContext()).apply {
            text = buildString {
                append(cert.issuer)
                cert.date?.let { append("  ·  $it") }
            }
            setTextAppearance(R.style.TextAppearance_Mohsin_BodySmall)
            setPadding(0, dp(6), 0, 0)
        })
        return card
    }

    private fun buildImpact(): View {
        metricAnims.clear()
        val wrap = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = matchParentWrap()
        }
        val cells = PortfolioData.impactMetrics
        var i = 0
        while (i < cells.size) {
            val row = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = matchParentWrap().apply { topMargin = dp(12) }
            }
            row.addView(buildMetricCell(cells[i].value, cells[i].label, 1f))
            if (i + 1 < cells.size) {
                row.addView(buildMetricCell(cells[i + 1].value, cells[i + 1].label, 1f))
            } else {
                row.addView(View(requireContext()).apply {
                    layoutParams = LinearLayout.LayoutParams(0, 1)
                })
            }
            wrap.addView(row)
            i += 2
        }
        wrap.addView(TextView(requireContext()).apply {
            text = "Figures are self-reported as stated in the résumé."
            setTextAppearance(R.style.TextAppearance_Mohsin_Mono)
            setPadding(0, dp(24), 0, dp(8))
        })
        return wrap
    }

    private fun buildMetricCell(value: String, label: String, weight: Float): View {
        val cell = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, weight)
            gravity = Gravity.START
        }
        val valueTv = TextView(requireContext()).apply {
            text = value
            setTextAppearance(R.style.TextAppearance_Mohsin_Metric)
        }
        val digits = value.takeWhile { it.isDigit() }
        if (digits.isNotEmpty()) {
            metricAnims.add(MetricAnim(valueTv, digits.toInt(), value.drop(digits.length)))
        }
        cell.addView(valueTv)
        cell.addView(TextView(requireContext()).apply {
            text = label
            setTextAppearance(R.style.TextAppearance_Mohsin_Caption)
            setPadding(0, dp(4), dp(8), 0)
        })
        return cell
    }

    private data class MetricAnim(val view: TextView, val target: Int, val suffix: String)

    private fun chipFlow(items: List<String>): View {
        val wrap = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = matchParentWrap().apply { topMargin = dp(8) }
        }
        var row = LinearLayout(requireContext()).apply { orientation = LinearLayout.HORIZONTAL }
        items.forEachIndexed { i, item ->
            if (i % 2 == 0 && i > 0) {
                wrap.addView(row)
                row = LinearLayout(requireContext()).apply { orientation = LinearLayout.HORIZONTAL }
            }
            row.addView(buildChip(item))
        }
        wrap.addView(row)
        return wrap
    }

    private fun sectionNote(text: String): TextView =
        TextView(requireContext()).apply {
            this.text = text
            setTextAppearance(R.style.TextAppearance_Mohsin_BodySmall)
            setPadding(0, dp(4), 0, dp(4))
        }

    private fun buildChip(text: String): TextView =
        TextView(requireContext()).apply {
            this.text = text
            setTextAppearance(R.style.TextAppearance_Mohsin_BodySmall)
            val hPad = dp(12)
            val vPad = dp(8)
            setPadding(hPad, vPad, hPad, vPad)
            setBackgroundResource(R.drawable.bg_chip)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                marginEnd = dp(8)
                bottomMargin = dp(8)
            }
        }

    private fun spacer(h: Int) = View(requireContext()).apply {
        layoutParams = LinearLayout.LayoutParams(1, dp(h))
    }

    private fun matchParentWrap() = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )

    private fun dp(v: Int) = (v * density).toInt()

    private fun getColorCompat(id: Int) = requireContext().getColor(id)

    private fun applyInsets() {
        val navBar = resources.getDimensionPixelSize(R.dimen.nav_bar_height)
        ViewCompat.setOnApplyWindowInsetsListener(binding.scrollView) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top, bottom = bars.bottom + navBar)
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
