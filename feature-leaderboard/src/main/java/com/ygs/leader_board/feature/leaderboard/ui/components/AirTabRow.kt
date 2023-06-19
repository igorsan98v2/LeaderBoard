package com.ygs.leader_board.feature.leaderboard.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ygs.domain.entity.LeaderBoardType
import com.ygs.leader_board.core.ui.SelectedTab
import com.ygs.leader_board.feature.leaderboard.R
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AirTabRow(pagerState: PagerState, modifier: Modifier, onClick: (LeaderBoardType) -> Unit) {
    // in this function we are creating a list
    // in this list we are specifying data as
    // name of the tab and the icon for it.
    val list = listOf(
        R.string.region to LeaderBoardType.REGIONAL,
        R.string.national to LeaderBoardType.NATIONAL,
        R.string.global to LeaderBoardType.GLOBAL
    )
    val coroutineScope = rememberCoroutineScope()

    // on below line we are creating
    // a variable for the scope.
    TabRow(
        modifier = modifier,
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .height(4.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp)) // clip modifier not working
                    .background(color = SelectedTab)
            )
        }
    ) {
        list.forEachIndexed { index, item ->
            Tab(
                selected = index == pagerState.currentPage,
                text = { Text(text = stringResource(id = item.first)) },
                onClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    onClick(list[index].second)
                },
            )
        }
    }
}

