package com.example.musemelody.view.topAppBar

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musemelody.constant.StyleConstant
import com.example.musemelody.view.button.BackButtonView
import com.example.musemelody.view.title.PrimaryTitleView

@Composable
fun PlainTopAppBarView(title: String, visible: Boolean) {
    Box {
        AnimatedVisibility(
            visible = visible, enter = fadeIn(), exit = fadeOut()
        ) {
            Surface(
                color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .height(StyleConstant.ROW_HEIGHT.dp)
                ) {}
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(StyleConstant.ROW_HEIGHT.dp)
                .padding(start = StyleConstant.PADDING_TINY.dp, end = StyleConstant.BUTTON_SIZE.dp + StyleConstant.PADDING_TINY.dp)
                .clickable {}, verticalAlignment = Alignment.CenterVertically
        ) {
            BackButtonView()
            PrimaryTitleView(title)
        }
    }
}