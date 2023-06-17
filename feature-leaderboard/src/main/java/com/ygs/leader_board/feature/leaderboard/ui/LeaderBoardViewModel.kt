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

package com.ygs.leader_board.feature.leaderboard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.ygs.leader_board.core.data.LeaderBoardRepository
import com.ygs.leader_board.feature.leaderboard.ui.LeaderBoardUiState.Error
import com.ygs.leader_board.feature.leaderboard.ui.LeaderBoardUiState.Loading
import com.ygs.leader_board.feature.leaderboard.ui.LeaderBoardUiState.Success
import javax.inject.Inject

@HiltViewModel
class LeaderBoardViewModel @Inject constructor(
    private val leaderBoardRepository: LeaderBoardRepository
) : ViewModel() {

    val uiState: StateFlow<LeaderBoardUiState> = leaderBoardRepository
        .leaderBoards.map<List<String>, LeaderBoardUiState> { Success(data = it) }
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun addLeaderBoard(name: String) {
        viewModelScope.launch {
            leaderBoardRepository.add(name)
        }
    }
}

sealed interface LeaderBoardUiState {
    object Loading : LeaderBoardUiState
    data class Error(val throwable: Throwable) : LeaderBoardUiState
    data class Success(val data: List<String>) : LeaderBoardUiState
}
