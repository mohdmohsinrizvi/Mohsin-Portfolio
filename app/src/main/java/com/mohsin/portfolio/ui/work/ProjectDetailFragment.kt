package com.mohsin.portfolio.ui.work

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
import com.mohsin.portfolio.animations.Anim.staggerChildren
import com.mohsin.portfolio.data.PortfolioData
import com.mohsin.portfolio.databinding.FragmentProjectDetailBinding
import com.mohsin.portfolio.models.Project
import com.mohsin.portfolio.utils.Links

class ProjectDetailFragment : Fragment() {

    private var _binding: FragmentProjectDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val project = PortfolioData.projects.find { it.id == arguments?.getString(ARG_ID) }
            ?: PortfolioData.projects.first()

        binding.topBar.backButton.setOnClickListener { parentFragmentManager.popBackStack() }
        binding.topBar.topBarTitle.text = ""

        binding.detailCategory.text = project.category.uppercase()
        binding.detailTitle.text = project.title
        binding.detailYear.text = project.year
        binding.detailDescription.text = project.description

        bindChips(project)
        bindMetrics(project)
        bindContributions(project)
        bindLinks(project)
        bindCaseStudy(project)

        (binding.scrollView.getChildAt(0) as? ViewGroup)?.staggerChildren(stepMs = 50)
        applyInsets()
    }

    private fun bindChips(project: Project) {
        val ctx = requireContext()
        project.technologies.forEach { tech ->
            binding.detailTechRow.addView(
                TextView(ctx).apply {
                    text = tech
                    setTextAppearance(R.style.TextAppearance_Mohsin_MonoPrimary)
                    val hPad = (10 * ctx.resources.displayMetrics.density).toInt()
                    val vPad = (6 * ctx.resources.displayMetrics.density).toInt()
                    setPadding(hPad, vPad, hPad, vPad)
                    setBackgroundResource(R.drawable.bg_chip)
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        marginEnd = (8 * ctx.resources.displayMetrics.density).toInt()
                        bottomMargin = (8 * ctx.resources.displayMetrics.density).toInt()
                    }
                }
            )
        }
    }

    private fun bindMetrics(project: Project) {
        val ctx = requireContext()
        val density = ctx.resources.displayMetrics.density
        project.metrics.forEach { metric ->
            val cell = LinearLayout(ctx).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
                gravity = Gravity.START
            }
            cell.addView(TextView(ctx).apply {
                text = metric.value
                setTextAppearance(R.style.TextAppearance_Mohsin_Metric)
            })
            cell.addView(TextView(ctx).apply {
                text = metric.label
                setTextAppearance(R.style.TextAppearance_Mohsin_Caption)
                setPadding(0, (4 * density).toInt(), 0, 0)
            })
            binding.detailMetrics.addView(cell)
        }
    }

    private fun bindContributions(project: Project) {
        val ctx = requireContext()
        val density = ctx.resources.displayMetrics.density
        project.contributions.forEach { item ->
            binding.detailContributions.addView(
                TextView(ctx).apply {
                    text = "—  $item"
                    setTextAppearance(R.style.TextAppearance_Mohsin_BodySecondary)
                    setPadding(0, (8 * density).toInt(), 0, (8 * density).toInt())
                }
            )
        }
    }

    private fun bindLinks(project: Project) {
        val ctx = requireContext()
        project.liveUrl?.let { url ->
            binding.detailLinks.addView(
                com.google.android.material.button.MaterialButton(ctx).apply {
                    text = "View Live Project"
                    setOnClickListener { Links.open(ctx, url) }
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply { bottomMargin = (10 * ctx.resources.displayMetrics.density).toInt() }
                }
            )
        }
        project.githubUrl?.let { url ->
            binding.detailLinks.addView(
                com.google.android.material.button.MaterialButton(
                ctx,
                null,
                com.google.android.material.R.attr.materialButtonOutlinedStyle
            ).apply {
                    text = "View on GitHub"
                    setOnClickListener { Links.open(ctx, url) }
                }
            )
        }
    }

    private fun bindCaseStudy(project: Project) {
        binding.detailCase.text = buildString {
            append(project.description)
            append("\n\n")
            append("Technologies used: ")
            append(project.technologies.joinToString(", "))
            append(".\n\n")
            append("Developed between ${project.year}. ")
            append(project.contributions.first())
        }
    }

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

    companion object {
        private const val ARG_ID = "project_id"
        fun newInstance(projectId: String) = ProjectDetailFragment().apply {
            arguments = Bundle().apply { putString(ARG_ID, projectId) }
        }
    }
}
