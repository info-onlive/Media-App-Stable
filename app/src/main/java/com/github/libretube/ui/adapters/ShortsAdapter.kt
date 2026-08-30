package com.github.libretube.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.libretube.api.obj.StreamItem
import com.github.libretube.databinding.ShortsRowBinding
import com.github.libretube.extensions.toID
import com.github.libretube.helpers.ImageHelper
import com.github.libretube.helpers.NavigationHelper
import com.github.libretube.parcelable.PlayerData
import com.github.libretube.ui.adapters.callbacks.DiffUtilItemCallback

class ShortsAdapter :
    ListAdapter<StreamItem, ShortsAdapter.ShortsViewHolder>(DiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortsViewHolder {
        val binding = ShortsRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShortsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShortsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ShortsViewHolder(
        private val binding: ShortsRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(video: StreamItem) {
            binding.shortTitle.text = video.title
            binding.shortChannel.text = video.uploaderName.orEmpty()

            ImageHelper.loadImage(video.thumbnail, binding.shortThumbnail)

            binding.root.setOnClickListener {
                val videoId = video.url.orEmpty().toID()
                NavigationHelper.navigateVideo(
                    binding.root.context,
                    PlayerData(videoId)
                )
            }
        }
    }
}
