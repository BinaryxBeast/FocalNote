package com.oneline.focalnote.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oneline.focalnote.R

@Composable
fun NoteBottomBar(
    lastEdited: String,
    onFontClick: () -> Unit,
    onPaletteClick: () -> Unit,
    onSwitchCategoryClick: () -> Unit
) {
    var iconColor = Color(0xFF37352F)
    var buttonBackgroundColor = Color(0xFFFFFFFF)
    var isActionMenuExpanded by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedIconButton(
                onClick = onFontClick,
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.LightGray),
                colors = IconButtonDefaults.outlinedIconButtonColors(
                    containerColor = buttonBackgroundColor
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_font_download_24),
                    contentDescription = "Change font",
                    tint = iconColor
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedIconButton(
                onClick = onPaletteClick,
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.LightGray),
                colors = IconButtonDefaults.outlinedIconButtonColors(
                    containerColor = buttonBackgroundColor
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_palette_24),
                    contentDescription = "Change palette",
                    tint = iconColor
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Last modified: 2 hrs ago",
                color = Color(0xFF787774),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            OutlinedIconButton(
                onClick = onPaletteClick,
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.LightGray),
                colors = IconButtonDefaults.outlinedIconButtonColors(
                    containerColor = buttonBackgroundColor
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_more_horiz_24),
                    contentDescription = "More options",
                    tint = iconColor
                )
            }
            DropdownMenu(
                expanded = isActionMenuExpanded,
                onDismissRequest = { isActionMenuExpanded = false },
                offset = DpOffset(x = (-160).dp, y = (-12).dp),
                modifier = Modifier
                    .width(220.dp)
                    .background(buttonBackgroundColor)
            ) {
                ActionMenuItem(
                    painter = painterResource(R.drawable.ic_star),
                    "Add to Favourites",
                    tint = Color(0xFF37352F),
                    onClick = {}
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteBottomBarPreview() {
    NoteBottomBar("", {}, {}, {})
}

@Composable
private fun ActionMenuItem(
    painter: Painter,
    label: String,
    tint: Color = Color(0xFF787774),
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painter,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = tint
        )
    }
}