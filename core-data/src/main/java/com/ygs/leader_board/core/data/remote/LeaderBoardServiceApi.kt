package com.ygs.leader_board.core.data.remote

import android.content.Context
import com.ygs.domain.entity.LeaderBoardType
import com.ygs.leader_board.core.data.remote.models.UserResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface LeaderBoardServiceApi {
    fun getLeaderBoard(type: LeaderBoardType): Flow<List<UserResponse>>
}

class FakeLeaderBoardServiceApi @Inject constructor(@ApplicationContext private val context: Context) :
    LeaderBoardServiceApi {

    override fun getLeaderBoard(type: LeaderBoardType): Flow<List<UserResponse>> =
        flow {
            val fileName = when (type) {
                LeaderBoardType.REGIONAL -> REGIONAL_RATING_FILE
                LeaderBoardType.NATIONAL -> NATIONAL_RATE_FILE
                LeaderBoardType.GLOBAL -> GLOBAL_RATE_FILE
            }
            lateinit var jsonString: String
            context.assets.open(fileName)
                .bufferedReader().use {
                    jsonString = it.readText()
                }
            delay(150)
            emit(Json.decodeFromString<List<UserResponse>>(jsonString).sortedByDescending { it.score })
        }

}

private const val GLOBAL_RATE_FILE = "global.json"
private const val NATIONAL_RATE_FILE = "national.json"
private const val REGIONAL_RATING_FILE = "region.json"