package com.github.libretube.api

import android.util.LruCache
import com.github.libretube.api.obj.Streams

class LocalStreamsExtractionPipedMediaServiceRepository : PipedMediaServiceRepository() {
    private val newPipeDelegate = NewPipeMediaServiceRepository()

    override suspend fun getStreams(videoId: String): Streams {
        streamCache.get(videoId)?.let { return it }

        return newPipeDelegate.getStreams(videoId).also {
            streamCache.put(videoId, it)
        }
    }

    companion object {
        private val streamCache = LruCache<String, Streams>(8)
    }
}
