/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ygs.leader_board.core.data.di

import android.content.Context
import com.ygs.domain.entity.LeaderBoardType
import com.ygs.domain.entity.User
import com.ygs.domain.repository.LeaderBoardRepository
import com.ygs.leader_board.core.data.DefaultLeaderBoardRepository
import com.ygs.leader_board.core.data.remote.FakeLeaderBoardServiceApi
import com.ygs.leader_board.core.data.remote.LeaderBoardServiceApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun provideLeaderBoardServiceApi(
        serviceAPI: FakeLeaderBoardServiceApi
    ): LeaderBoardServiceApi

    @Singleton
    @Binds
    fun bindsLeaderBoardRepository(
        leaderBoardRepository: DefaultLeaderBoardRepository
    ): LeaderBoardRepository


}

class FakeLeaderBoardRepository @Inject constructor() : LeaderBoardRepository {
    override fun getLeaderBoard(type: LeaderBoardType): Flow<List<User>> {
        return flowOf(fakeLeaderBoards)
    }

}

val fakeLeaderBoards =
    listOf(User(name = "test", username = "somekind", score = 44, isRaised = true))
