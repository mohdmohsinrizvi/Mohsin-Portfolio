package com.mohsin.portfolio.ui.work

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohsin.portfolio.MainActivity
import com.mohsin.portfolio.R
import com.mohsin.portfolio.animations.Anim.fadeUpIn
import com.mohsin.portfolio.data.PortfolioData
import com.mohsin.portfolio.databinding.FragmentWorkBinding

class WorkFragment : Fragment() {

    private var _binding: FragmentWorkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.topBar.topBarTitle.text = "Work"
        binding.topBar.backButton.visibility = View.GONE
        binding.workTitle.text = "Selected Work"
        binding.workSubtitle.text =
            "A curated selection of web applications built with a focus on usability, performance, and consistency."

        binding.projectList.layoutManager = LinearLayoutManager(requireContext())
        binding.projectList.adapter = ProjectAdapter(PortfolioData.projects) { project ->
            (activity as MainActivity).openProject(project.id)
        }

        binding.workTitle.fadeUpIn()
        binding.workSubtitle.fadeUpIn(startDelayMs = 90)
        binding.projectList.post {
            for (i in 0 until binding.projectList.childCount) {
                binding.projectList.getChildAt(i).fadeUpIn(startDelayMs = 180L + 55L * i)
            }
        }

        applyInsets()
    }

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
