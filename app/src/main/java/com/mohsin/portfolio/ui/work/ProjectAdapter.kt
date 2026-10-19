package com.mohsin.portfolio.ui.work

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mohsin.portfolio.R
import com.mohsin.portfolio.databinding.ItemProjectBinding
import com.mohsin.portfolio.models.Project

class ProjectAdapter(
    private val projects: List<Project>,
    private val onClick: (Project) -> Unit
) : RecyclerView.Adapter<ProjectAdapter.Holder>() {

    class Holder(val binding: ItemProjectBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount() = projects.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val project = projects[position]
        val b = holder.binding
        val ctx = b.root.context
        val density = ctx.resources.displayMetrics.density
        val dp = { v: Int -> (v * density).toInt() }

        b.projectIndex.text = project.index
        b.projectCategory.text = project.category.uppercase()
        b.projectTitle.text = project.title
        b.projectDescription.text = project.description
        b.projectYear.text = project.year

        b.techContainer.removeAllViews()
        project.technologies.forEach { tech ->
            b.techContainer.addView(
                TextView(ctx).apply {
                    text = tech
                    setTextAppearance(R.style.TextAppearance_Mohsin_MonoPrimary)
                    setPadding(0, dp(4), 0, dp(4))
                }
            )
        }

        // Alternating editorial layout: even = text left / meta right, odd = reversed
        b.itemRow.removeAllViews()
        if (position % 2 == 0) {
            b.itemRow.gravity = Gravity.TOP
            b.textCol.layoutParams = (b.textCol.layoutParams as LinearLayout.LayoutParams).apply {
                width = 0; weight = 1f; marginEnd = 0
            }
            b.metaCol.layoutParams = (b.metaCol.layoutParams as LinearLayout.LayoutParams).apply {
                width = ViewGroup.LayoutParams.WRAP_CONTENT; marginStart = dp(16)
            }
            b.itemRow.addView(b.textCol)
            b.itemRow.addView(b.metaCol)
        } else {
            b.itemRow.gravity = Gravity.TOP
            b.textCol.layoutParams = (b.textCol.layoutParams as LinearLayout.LayoutParams).apply {
                width = 0; weight = 1f; marginEnd = 0
            }
            b.metaCol.layoutParams = (b.metaCol.layoutParams as LinearLayout.LayoutParams).apply {
                width = ViewGroup.LayoutParams.WRAP_CONTENT; marginStart = 0; marginEnd = dp(16)
            }
            b.metaCol.gravity = Gravity.START
            b.techContainer.gravity = Gravity.START
            b.itemRow.addView(b.metaCol)
            b.itemRow.addView(b.textCol)
        }

        b.projectRoot.setOnClickListener { onClick(project) }
    }
}
