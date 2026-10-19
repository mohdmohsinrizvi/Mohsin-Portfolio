package com.mohsin.portfolio.ui.connect

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.mohsin.portfolio.MainActivity
import com.mohsin.portfolio.R
import com.mohsin.portfolio.animations.Anim.staggerChildren
import com.mohsin.portfolio.data.PortfolioData
import com.mohsin.portfolio.databinding.FragmentConnectBinding
import com.mohsin.portfolio.utils.Links

class ConnectFragment : Fragment() {

    private var _binding: FragmentConnectBinding? = null
    private val binding get() = _binding!!
    private val density get() = requireContext().resources.displayMetrics.density

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConnectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.topBar.topBarTitle.text = "Connect"
        binding.topBar.backButton.visibility = View.GONE

        val c = PortfolioData.contact
        binding.connectHeading.text = "Let’s create something exceptional."
        binding.connectSub.text =
            "Open to meaningful opportunities, collaborations, and conversations around technology, design, and innovation."
        binding.availability.text = c.availability
        binding.workAuth.text = c.workAuthorization
        binding.locationLabel.text = c.location.uppercase()

        binding.contactList.addView(
            contactRow(R.drawable.ic_mail, "Email", c.email) { Links.email(requireContext(), c.email) }
        )
        binding.contactList.addView(
            contactRow(R.drawable.ic_content_copy, "Copy email", null) {
                Links.copy(requireContext(), c.email, "email")
            }
        )
        binding.contactList.addView(
            contactRow(R.drawable.ic_call, "Phone", c.phone) { Links.dial(requireContext(), c.phone) }
        )

        binding.socialList.addView(
            contactRow(R.drawable.ic_open_link, "LinkedIn", c.linkedIn.removePrefix("https://")) {
                Links.open(requireContext(), c.linkedIn)
            }
        )
        binding.socialList.addView(
            contactRow(R.drawable.ic_open_link, "GitHub", c.gitHub.removePrefix("https://")) {
                Links.open(requireContext(), c.gitHub)
            }
        )
        binding.socialList.addView(
            contactRow(R.drawable.ic_open_link, "Portfolio", c.portfolio.removePrefix("https://")) {
                Links.open(requireContext(), c.portfolio)
            }
        )

        c.languages.forEach { lang ->
            binding.languageList.addView(TextView(requireContext()).apply {
                text = lang
                setTextAppearance(R.style.TextAppearance_Mohsin_BodySecondary)
                setPadding(0, dp(6), 0, dp(6))
            })
        }

        c.interests.forEach { interest ->
            binding.interestChips.addView(TextView(requireContext()).apply {
                text = interest
                setTextAppearance(R.style.TextAppearance_Mohsin_BodySmall)
                setPadding(dp(12), dp(8), dp(12), dp(8))
                setBackgroundResource(R.drawable.bg_chip)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = dp(8) }
            })
        }

        binding.viewResume.setOnClickListener { (activity as MainActivity).openResume() }
        binding.shareProfile.setOnClickListener { Links.shareProfile(requireContext()) }

        (binding.scrollView.getChildAt(0) as? ViewGroup)?.staggerChildren(stepMs = 55)

        applyInsets()
    }

    private fun contactRow(iconRes: Int, title: String, subtitle: String?, onClick: () -> Unit): View {
        val ctx = requireContext()
        val row = LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            isClickable = true
            isFocusable = true
            minimumHeight = dp(56)
            val padV = dp(10)
            setPadding(0, padV, 0, padV)
            setBackgroundResource(android.R.drawable.list_selector_background)
            setOnClickListener { onClick() }
        }
        row.addView(ImageView(ctx).apply {
            setImageResource(iconRes)
            setColorFilter(requireContext().getColor(R.color.colorTextSecondary))
            layoutParams = LinearLayout.LayoutParams(dp(22), dp(22)).apply { marginEnd = dp(16) }
            importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        })
        val textCol = LinearLayout(ctx).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
        }
        textCol.addView(TextView(ctx).apply {
            text = title
            setTextAppearance(R.style.TextAppearance_Mohsin_SectionTitle)
            textSize = 15f
        })
        subtitle?.let {
            textCol.addView(TextView(ctx).apply {
                text = it
                setTextAppearance(R.style.TextAppearance_Mohsin_BodySmall)
                setPadding(0, dp(2), 0, 0)
            })
        }
        row.addView(textCol)
        return row
    }

    private fun dp(v: Int) = (v * density).toInt()

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
