package com.example.musemelody.view.cell

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.musemelody.constant.StyleConstant
import com.example.musemelody.repository.realm.ItemType
import dev.tcode.thinmp.view.image.ImageView
import com.example.musemelody.view.text.PrimaryTextView
import com.example.musemelody.view.text.SecondaryTextView

@Composable
fun ShortcutCellView(
    primaryText: String, secondaryText: String, uri: Uri, type: ItemType, modifier: Modifier = Modifier
) {
    BoxWithConstraints {
        val size = with(LocalDensity.current) { constraints.maxWidth.toDp() }

        Column(modifier = modifier) {
            Box {
                if (type === ItemType.ARTIST) {
                    ImageView(
                        uri = uri, contentScale = ContentScale.FillWidth, modifier = Modifier
                            .size(size)
                            .clip(CircleShape)
                    )
                } else {
                    ImageView(
                        uri = uri, modifier = Modifier
                            .width(size)
                            .height(size)
                            .clip(RoundedCornerShape(StyleConstant.IMAGE_CORNER_SIZE.dp))
                    )
                }
            }
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = StyleConstant.PADDING_TINY.dp)
            ) {
                PrimaryTextView(primaryText)
            }
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()
            ) {
                SecondaryTextView(secondaryText)
            }
        }
    }
}