package com.ygs.leader_board.feature.leaderboard.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.ygs.domain.entity.User
import com.ygs.leader_board.core.ui.FirstPlace
import com.ygs.leader_board.core.ui.SecondPlace
import com.ygs.leader_board.core.ui.ThirdPlace
import com.ygs.leader_board.feature.leaderboard.R

const val MARK_ID = "highestScoreMark"
const val FIRST_PLACE_ID = "firstPlace"
const val SECOND_PLACE_ID = "secondPlace"
const val THIRD_PLACE_ID = "thirdPlace"
const val PEDESTAL_ID = "pedestal"

@Composable
fun TopScoredUsers(users: List<User>, modifier: Modifier = Modifier) {
    Box(modifier) {
        ConstraintLayout(decoupledConstraints) {
            Box(
                Modifier
                    .size(34.dp)
                    .layoutId(MARK_ID)
            ) {
                Image(
                    painterResource(id = R.drawable.ic_top_placed),
                    "",
                    modifier = Modifier
                        .size(width = 27.dp, height = 34.dp)
                        .border(0.dp, Color.Transparent)
                        .align(Alignment.CenterEnd)
                )
            }
            Icon(
                painterResource(id = R.drawable.person_48px),
                "",
                modifier = Modifier
                    .size(82.dp)
                    .border(0.dp, Color.Transparent)
                    .border(4.dp, FirstPlace, CircleShape)
                    .layoutId(FIRST_PLACE_ID)
            )
            Icon(
                painterResource(id = R.drawable.person_48px),
                "",
                modifier = Modifier
                    .size(82.dp)
                    .border(4.dp, SecondPlace, CircleShape)
                    .layoutId(SECOND_PLACE_ID)
            )
            Icon(
                painterResource(id = R.drawable.person_48px),
                "",
                modifier = Modifier
                    .size(82.dp)
                    .border(4.dp, ThirdPlace, CircleShape)
                    .layoutId(THIRD_PLACE_ID)
            )
            Pedestal(
                Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .layoutId(PEDESTAL_ID),
                users = users
            )

        }
    }
}

private val decoupledConstraints =
    ConstraintSet {
        val mark = createRefFor(MARK_ID)
        val firstPlaceIcon = createRefFor(FIRST_PLACE_ID)
        val firstPlaceColumn = createRefFor(PEDESTAL_ID)
        val secondPlaceImage = createRefFor(SECOND_PLACE_ID)
        val thirdPlaceIcon = createRefFor(THIRD_PLACE_ID)
        //or use translation x/y instead
        constrain(mark) {
            top.linkTo(parent.top, margin = 21.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)

        }
        constrain(firstPlaceIcon) {
            top.linkTo(parent.top, margin = 49.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            translationZ = 1.dp
        }
        constrain(firstPlaceColumn) {
            top.linkTo(parent.top, margin = 98.dp)
            width = Dimension.matchParent
            translationZ = 0.dp
        }
        constrain(secondPlaceImage) {
            top.linkTo(parent.top, margin = 90.dp)
            start.linkTo(parent.start, margin = 24.dp)
            translationZ = 1.dp
        }
        constrain(thirdPlaceIcon) {
            top.linkTo(parent.top, margin = 90.dp)
            end.linkTo(parent.end, margin = 24.dp)
            translationZ = 1.dp
        }

    }

@Composable
fun Pedestal(modifier: Modifier, users: List<User>) {
    Box(modifier = modifier) {
        Column(
            Modifier
                .height(160.dp)
                .width(122.dp)
                .align(Alignment.Center)
                .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .zIndex(3.0f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PedestalText(users.firstOrNull())
        }
        Row(
            Modifier
                .height(113.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.tertiary)
                .zIndex(2.5f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Spacer(Modifier.size(28.dp))
                Column {
                    PedestalText(
                        users.getOrNull(1),
                        highScoreColor = SecondPlace,
                        topTextPaddingTop = 29.dp,
                        lastTextPaddingTop = 9.dp
                    )
                }
            }
            Row {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    PedestalText(
                        users.getOrNull(2),
                        highScoreColor = ThirdPlace,
                        topTextPaddingTop = 30.dp,
                        lastTextPaddingTop = 6.dp
                    )
                }
                Spacer(Modifier.size(28.dp))

            }
        }
    }
}

@Composable
fun PedestalText(
    user: User?,
    highScoreColor: Color = FirstPlace,
    topTextPaddingTop: Dp = 70.dp,
    lastTextPaddingTop: Dp = 13.dp
) {
    Text(
        user?.username
            ?: stringResource(id = R.string.no_users_placed_so_far),
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(top = topTextPaddingTop)
    )
    Text(
        "${user?.score ?: ""}",
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = highScoreColor
    )
    Text(
        user?.username ?: stringResource(id = R.string.no_users_placed_so_far_1),
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Light,
        modifier = Modifier.padding(top = lastTextPaddingTop)
    )
}

