package com.ygs.leader_board.core.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val name: String,
    val username: String,
    val score: Int,
    val isRaised: Boolean
)