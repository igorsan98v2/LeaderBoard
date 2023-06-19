package com.ygs.domain.repository

import com.ygs.domain.entity.LeaderBoardType
import com.ygs.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface LeaderBoardRepository {
   fun getLeaderBoard(type: LeaderBoardType): Flow<List<User>>
}

