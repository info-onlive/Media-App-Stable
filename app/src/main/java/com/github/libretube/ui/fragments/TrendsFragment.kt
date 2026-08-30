package com.github.libretube.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.libretube.R
import com.github.libretube.api.MediaServiceRepository
import com.github.libretube.constants.PreferenceKeys
import com.github.libretube.databinding.FragmentTrendsBinding
import com.github.libretube.helpers.LocaleHelper
import com.github.libretube.helpers.PreferenceHelper
import com.github.libretube.ui.adapters.ShortsAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrendsFragment : Fragment(R.layout.fragment_trends) {

    private var _binding: FragmentTrendsBinding? = null
    private val binding get() = _binding!!

    private val shortsAdapter = ShortsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentTrendsBinding.bind(view)

        binding.pager.orientation =
            androidx.viewpager2.widget.ViewPager2.ORIENTATION_VERTICAL

        binding.pager.adapter = shortsAdapter

        loadShorts()
    }

    private fun loadShorts() {
        viewLifecycleOwner.lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                MediaServiceRepository.instance
                    .getSearchResults("shorts", "videos")
            }

            Log.d("SHORTS_TEST", "total=${result.items.size}")
            Log.d(
                "SHORTS_TEST",
                "markedShorts=${result.items.count { it.isShort == true }}"
            )

            result.items.forEachIndexed { index, item ->
                Log.d("SHORTS_TEST", "item[$index] type=${item.type} url=${item.url}")
            }

            val shorts = result.items
                .filter { it.type == "stream" || it.type == "video" }
                .map { it.toStreamItem() }
                .take(20)

            Log.d("SHORTS_TEST", "submitted=${shorts.size}")

            shortsAdapter.submitList(shorts)
        }
    }


    companion object {
        fun showChangeRegionDialog(context: Context, onPositiveButtonClick: () -> Unit) {
            val currentRegionPref = PreferenceHelper.getTrendingRegion(context)
            val countries = LocaleHelper.getAvailableCountries()
            var selected = countries.indexOfFirst { it.code == currentRegionPref }

            MaterialAlertDialogBuilder(context)
                .setTitle(R.string.region)
                .setSingleChoiceItems(
                    countries.map { it.name }.toTypedArray(),
                    selected
                ) { _, checked ->
                    selected = checked
                }
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.okay) { _, _ ->
                    PreferenceHelper.putString(
                        PreferenceKeys.REGION,
                        countries[selected].code
                    )
                    onPositiveButtonClick()
                }
                .show()
        }
    }

    override fun onDestroyView() {
        binding.pager.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
