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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.oneline.focalnote.data.FontType
import com.oneline.focalnote.data.NoteDataBase
import com.oneline.focalnote.repository.NoteRepository
import com.oneline.focalnote.viewmodel.NoteViewModel

@Composable
fun QuickCaptureScreen(
    inputText: String = "",
    onTextChange: (String) -> Unit = {},
    onEditClick: () -> Unit = {},
    viewModel: NoteViewModel = run {
        val context = LocalContext.current.applicationContext
        viewModel(
            factory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val db = Room.databaseBuilder(context, NoteDataBase::class.java, "note_database").build()
                    return NoteViewModel(NoteRepository(db.noteDao())) as T
                }
            }
        )
    }
) {
    val uiState by viewModel.uiState.collectAsState()
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
                {
                    viewModel.updateFont()
                    FontType.DEFAULT
                },
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
            val currentText = uiState.text.ifEmpty { inputText }
            val words = currentText.split(" ")

            val fontFamily = uiState.fontType.toFontFamily()
            while (dynamicFontSize > minFontSize) {

                val measuredText = textMeasurer.measure(
                    text = currentText.ifEmpty { " " },
                    style = TextStyle(
                        fontSize = dynamicFontSize,
                        fontWeight = FontWeight.Bold,
                        lineHeight = dynamicFontSize * 1.1f,
                        fontFamily = fontFamily
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
                value = uiState.text,
                onValueChange = viewModel::updateText,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                textStyle = TextStyle(
                    fontSize = dynamicFontSize,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    lineHeight = dynamicFontSize * 1.1f,
                    color = Color.Black,
                    fontFamily = fontFamily
                ),
                cursorBrush = SolidColor(Color(0xFF1976D2))
            )
        }
    }
}
