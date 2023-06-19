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
import com.ygs.domain.entity.LeaderBoardType
import com.ygs.domain.entity.User
import com.ygs.domain.repository.LeaderBoardRepository
import com.ygs.leader_board.feature.leaderboard.ui.LeaderBoardUiState.Error
import com.ygs.leader_board.feature.leaderboard.ui.LeaderBoardUiState.Loading
import com.ygs.leader_board.feature.leaderboard.ui.LeaderBoardUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderBoardViewModel @Inject constructor(
    private val leaderBoardRepository: LeaderBoardRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<LeaderBoardUiState> =
        MutableStateFlow(Loading(LeaderBoardType.GLOBAL))

    val uiState: StateFlow<LeaderBoardUiState> = _uiState

    fun onAction(action: LeaderBoardAction) {
        when (action) {
            LeaderBoardAction.FetchData -> viewModelScope.launch { fetchLeaderBoards() }
        }
    }

    private suspend fun fetchLeaderBoards() {
        val map: MutableMap<LeaderBoardType, List<User>> = mutableMapOf()
        LeaderBoardType.values().forEach { type ->

            leaderBoardRepository.getLeaderBoard(type)
                .catch { error -> _uiState.value = Error(error) }
                .collect { list ->
                    map[type] = list
                    _uiState.value = Success(map)
                }

        }
    }

}


sealed interface LeaderBoardUiState {

    data class Loading(val type: LeaderBoardType) : LeaderBoardUiState
    data class Error(val throwable: Throwable) : LeaderBoardUiState
    data class Success(val data: Map<LeaderBoardType, List<User>>) : LeaderBoardUiState
}

sealed interface LeaderBoardAction {
    object FetchData : LeaderBoardAction
}