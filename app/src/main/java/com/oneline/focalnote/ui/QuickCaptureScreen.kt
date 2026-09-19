package com.oneline.focalnote.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.sp

@Composable
fun QuickCaptureScreen(
    inputText: String,
    onTextChange: (String) -> Unit,
    onEditClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val textMeasurer = rememberTextMeasurer()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }



    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            NoteBottomBar(
                "5:39",
                {},
                {},
                {}
            )
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding(),
            contentAlignment = Alignment.Center
        ) {
            val maxWidthPx = constraints.maxWidth
            val maxHeightPx = constraints.maxHeight


            val safeMaxWidthPx = maxWidthPx - 20

            var dynamicFontSize = 500.sp
            val minFontSize = 18.sp
            val words = inputText.split(" ")

            while (dynamicFontSize > minFontSize) {

                val measuredText = textMeasurer.measure(
                    text = inputText.ifEmpty { " " },
                    style = TextStyle(
                        fontSize = dynamicFontSize,
                        fontWeight = FontWeight.Bold,
                        lineHeight = dynamicFontSize * 1.1f
                    ),
                    constraints = Constraints(maxWidth = safeMaxWidthPx)
                )


                val hasWordOverflow = words.any { word ->
                    textMeasurer.measure(
                        text = word,
                        style = TextStyle(
                            fontSize = dynamicFontSize,
                            fontWeight = FontWeight.Bold
                        )
                    ).size.width > safeMaxWidthPx
                }

                if (measuredText.size.height <= maxHeightPx && !measuredText.hasVisualOverflow && !hasWordOverflow) {
                    break
                }
                dynamicFontSize = (dynamicFontSize.value * 0.95f).sp
            }

            BasicTextField(
                value = inputText,
                onValueChange = onTextChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                textStyle = TextStyle(
                    fontSize = dynamicFontSize,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    lineHeight = dynamicFontSize * 1.1f,
                    color = Color.Black
                ),
                cursorBrush = SolidColor(Color(0xFF1976D2))
            )
        }
    }
}

