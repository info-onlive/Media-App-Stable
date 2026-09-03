package com.github.libretube.api.obj

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivationResponse(
    val ok: Boolean,
    val reason: String? = null,
    @SerialName("expires_at")
    val expiresAt: String? = null,
    @SerialName("days_left")
    val daysLeft: Int? = null,
    @SerialName("hours_left")
    val hoursLeft: Int? = null,
    @SerialName("seconds_left")
    val secondsLeft: Long? = null
)
