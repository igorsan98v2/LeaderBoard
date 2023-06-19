package com.ygs.leader_board.feature.leaderboard.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ygs.domain.entity.User
import com.ygs.leader_board.feature.leaderboard.R

@Composable
fun LeaderboardList(list: List<User>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 42.dp,
                    topEnd = 42.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
            )
            .background(MaterialTheme.colorScheme.tertiary),
        contentPadding = PaddingValues(start = 25.dp, end = 25.dp)
    ) {
        item {
            Spacer(Modifier.size(5.dp))//it removes flag is first
        }
        itemsIndexed(items = list, key = { _, item -> item.username + item.score }) { index, item ->
            LeaderboardItem(user = item, isLast = index == list.lastIndex)
        }
    }
}

@Composable
fun LeaderboardItem(user: User, isLast: Boolean) {
    Column(Modifier.fillMaxWidth()) {
        Spacer(Modifier.size(18.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.person_48px),
                    contentDescription = "avatar"
                )
                Spacer(Modifier.size(23.dp))
                Column {
                    Text(
                        user.name, style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = user.username, style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Light
                    )
                }
            }
            Column {
                Text(
                    text = user.score.toString(), style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(6.dp))
                Image(
                    painterResource(
                        id = if (user.isRaised) com.ygs.leader_board.core.ui.R.drawable.ic_positive_trend
                        else com.ygs.leader_board.core.ui.R.drawable.ic_negative_trend,
                    ),
                    "",
                    modifier = Modifier.size(13.dp)
                )

            }

        }
        if (!isLast) {
            Divider(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            )
        }
    }
}