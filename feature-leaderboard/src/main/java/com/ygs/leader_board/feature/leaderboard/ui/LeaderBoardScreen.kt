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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ygs.domain.entity.LeaderBoardType
import com.ygs.domain.entity.User
import com.ygs.leader_board.core.ui.MyApplicationTheme
import com.ygs.leader_board.feature.leaderboard.R
import com.ygs.leader_board.feature.leaderboard.ui.LeaderBoardUiState.Success
import com.ygs.leader_board.feature.leaderboard.ui.components.AirTabRow
import com.ygs.leader_board.feature.leaderboard.ui.components.LeaderboardList
import com.ygs.leader_board.feature.leaderboard.ui.components.TopScoredUsers

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LeaderBoardScreen(
    modifier: Modifier = Modifier,
    viewModel: LeaderBoardViewModel = hiltViewModel()
) {
    val items by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) {
        viewModel.onAction(LeaderBoardAction.FetchData)
    }
    if (items is Success) {
        val pagerState = rememberPagerState(0)
        LeaderBoardScreen(
            map = (items as Success).data,
            pagerState = pagerState,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun LeaderBoardScreen(
    map: Map<LeaderBoardType, List<User>>,
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    selectedType: LeaderBoardType = LeaderBoardType.GLOBAL,

    ) {
    val list = map[selectedType] ?: listOf()

    Column(modifier) {
        Text(
            stringResource(id = R.string.leaderboard),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        AirTabRow(
            pagerState = pagerState,
            modifier = Modifier.padding(top = 21.dp),
            onClick = {/*in case of update and so on*/ })
        TopScoredUsers(
            list.subList(0, 3),
            modifier = Modifier.fillMaxWidth()
        )
        HorizontalPager(
            pageCount = map.size,
            state = pagerState
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
            ) {
                LeaderboardList(list = list)
            }
        }
    }

}


// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {

    }
}

