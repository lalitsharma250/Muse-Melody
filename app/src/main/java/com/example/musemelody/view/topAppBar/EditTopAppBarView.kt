package com.example.musemelody.view.topAppBar

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.musemelody.R
import com.example.musemelody.constant.StyleConstant
import com.example.musemelody.view.nav.LocalNavigator
import com.example.musemelody.view.title.PrimaryTitleView

@Composable
fun EditTopAppBarView(title: String, offset: Int, content: @Composable BoxScope.() -> Unit) {
    val navigator = LocalNavigator.current

    Box {
        AnimatedVisibility(
            visible = offset > 1, enter = fadeIn(initialAlpha = 0.3F), exit = fadeOut(targetAlpha = 0.3F)
        ) {
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.onBackground)
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(StyleConstant.ROW_HEIGHT.dp)
            ) {}
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(StyleConstant.ROW_HEIGHT.dp)
                .padding(start = StyleConstant.PADDING_LARGE.dp, end = StyleConstant.PADDING_LARGE.dp)
                .clickable {},
        ) {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
                    .clip(RoundedCornerShape(StyleConstant.IMAGE_CORNER_SIZE.dp))
                    .clickable { navigator.back() }) {
                Text(stringResource(R.string.cancel), color = MaterialTheme.colorScheme.primary)
            }
            Box(contentAlignment = Alignment.Center, modifier = Modifier.align(alignment = Alignment.Center)) {
                PrimaryTitleView(title)
            }
            Box(content = content, contentAlignment = Alignment.Center, modifier = Modifier.align(alignment = Alignment.CenterEnd))
        }
    }
}