package com.onionsquare.goabase.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val GoabaseColorPalette = darkColors(
        primary = PurpleDark,
        secondary = PurpleLight,
        background = PurpleDark
)


val typography = Typography(
        h1 = TextStyle(
        ),
        h2 = TextStyle(
        ),
        h3 = TextStyle(
        ),
        h4 = TextStyle(
                color = AlienGreen,
                fontWeight = FontWeight.W700,
                fontSize = 34.sp
        ),
        h5 = TextStyle(
                color = AlienGreen,
                fontWeight = FontWeight.W700,
                fontSize = 20.sp
        ),
        h6 = TextStyle(
                color = Mustard,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
        ),
        subtitle1 = TextStyle(
        ),
        subtitle2 = TextStyle(
        ),
        body1 = TextStyle(
                color = Mustard
        ),
        body2 = TextStyle(
                color = PurpleDark
        ),
        button = TextStyle(
        ),
        caption = TextStyle(
        ),
        overline = TextStyle(
        ),
)

@Composable
fun GoabaseTheme(content: @Composable () -> Unit) {


    MaterialTheme(
            colors = GoabaseColorPalette,
            content = content,
            typography = typography
    )

}