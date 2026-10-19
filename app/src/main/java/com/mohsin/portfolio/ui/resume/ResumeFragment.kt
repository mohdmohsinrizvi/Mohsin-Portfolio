package com.mohsin.portfolio.ui.resume

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.mohsin.portfolio.R
import com.mohsin.portfolio.databinding.FragmentResumeBinding
import com.mohsin.portfolio.utils.Links
import java.io.File
import java.io.FileOutputStream

class ResumeFragment : Fragment() {

    private var _binding: FragmentResumeBinding? = null
    private val binding get() = _binding!!

    private var pdfFile: File? = null
    private var renderer: PdfRenderer? = null
    private var pageIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResumeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.topBar.topBarTitle.text = "Résumé"
        binding.topBar.backButton.setOnClickListener { parentFragmentManager.popBackStack() }

        binding.prevPage.setOnClickListener { step(-1) }
        binding.nextPage.setOnClickListener { step(1) }
        binding.openExternal.setOnClickListener {
            pdfFile?.let { f -> Links.openFile(requireContext(), f, "application/pdf") }
        }
        binding.shareResume.setOnClickListener {
            pdfFile?.let { f ->
                Links.shareFile(requireContext(), f, "application/pdf", "Résumé")
            }
        }

        openPdf()
        applyInsets()
    }

    private fun openPdf() {
        try {
            val file = copyAssetToCache("resume.pdf")
            pdfFile = file
            renderer = PdfRenderer(android.os.ParcelFileDescriptor.open(file, android.os.ParcelFileDescriptor.MODE_READ_ONLY))
            pageIndex = 0
            render()
            binding.errorText.visibility = View.GONE
        } catch (e: Exception) {
            binding.errorText.visibility = View.VISIBLE
            binding.pageImage.visibility = View.GONE
            binding.prevPage.isEnabled = false
            binding.nextPage.isEnabled = false
            binding.openExternal.isEnabled = false
            binding.shareResume.isEnabled = false
        }
    }

    private fun copyAssetToCache(name: String): File {
        val dir = File(requireContext().cacheDir, "resume").apply { mkdirs() }
        val out = File(dir, name)
        if (!out.exists()) {
            requireContext().assets.open(name).use { input ->
                FileOutputStream(out).use { input.copyTo(it) }
            }
        }
        return out
    }

    private fun step(delta: Int) {
        val r = renderer ?: return
        val next = pageIndex + delta
        if (next < 0 || next >= r.pageCount) return
        pageIndex = next
        render()
    }

    private fun render() {
        val r = renderer ?: return
        val page = r.openPage(pageIndex)
        try {
            val scale = 2.2f
            val w = (page.width * scale).toInt()
            val h = (page.height * scale).toInt()
            val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            bitmap.eraseColor(Color.WHITE)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            binding.pageImage.setImageBitmap(bitmap)
        } finally {
            page.close()
        }
        binding.pageIndicator.text = getString(R.string.page_of, pageIndex + 1, r.pageCount)
        binding.prevPage.isEnabled = pageIndex > 0
        binding.nextPage.isEnabled = pageIndex < r.pageCount - 1
    }

    private fun applyInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = bars.top, bottom = bars.bottom)
            insets
        }
    }

    override fun onDestroyView() {
        renderer?.close()
        renderer = null
        super.onDestroyView()
        _binding = null
    }
}
