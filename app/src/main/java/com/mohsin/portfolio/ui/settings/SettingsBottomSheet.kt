package com.mohsin.portfolio.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mohsin.portfolio.R
import com.mohsin.portfolio.data.ThemePrefs
import com.mohsin.portfolio.databinding.BottomSheetSettingsBinding

class SettingsBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.themeTitle.text = getString(R.string.theme_title)
        binding.optionSystem.text = getString(R.string.theme_system)
        binding.optionDark.text = getString(R.string.theme_dark)
        binding.optionLight.text = getString(R.string.theme_light)

        val current = ThemePrefs.mode(requireContext())
        when (current) {
            ThemePrefs.MODE_DARK -> binding.themeGroup.check(R.id.optionDark)
            ThemePrefs.MODE_LIGHT -> binding.themeGroup.check(R.id.optionLight)
            else -> binding.themeGroup.check(R.id.optionSystem)
        }

        binding.themeGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val mode = when (checkedId) {
                    R.id.optionDark -> ThemePrefs.MODE_DARK
                    R.id.optionLight -> ThemePrefs.MODE_LIGHT
                    else -> ThemePrefs.MODE_SYSTEM
                }
                ThemePrefs.setMode(requireContext(), mode)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
