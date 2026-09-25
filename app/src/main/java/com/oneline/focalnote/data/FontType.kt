package com.oneline.focalnote.data

import androidx.compose.ui.text.font.FontFamily


enum class FontType {
    DEFAULT,
    SANS,
    SERIF,
    MONO;
    fun toFontFamily(): FontFamily = when(this){
        DEFAULT -> FontFamily.Default
        SANS-> FontFamily.SansSerif
        SERIF -> FontFamily.Serif
        MONO-> FontFamily.Monospace
    }

    fun next(): FontType = when (this){
        DEFAULT -> SANS
        SANS -> SERIF
        SERIF -> MONO
        MONO -> DEFAULT
    }
}

