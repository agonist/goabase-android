package com.onionsquare.goabase.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.onionsquare.goabase.R

private val GoabaseColorPalette = darkColors(
        primary = PurpleDark,
        secondary = PurpleLight,
        background = PurpleDark
)

private val RobotoCondensed = FontFamily(
        Font(R.font.robotocondensed_regular),
        Font(R.font.robotocondensed_light, FontWeight.Light),
        Font(R.font.robotocondensed_bold, FontWeight.Bold)
)

val typography = Typography(
        defaultFontFamily = RobotoCondensed,
        h1 = TextStyle(
                fontWeight = FontWeight.W100,
                fontSize = 96.sp,
        ),
        h2 = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 44.sp,
                letterSpacing = 1.5.sp
        ),
        h3 = TextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 14.sp
        ),
        h4 = TextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 34.sp
        ),
        h5 = TextStyle(
                fontWeight = FontWeight.W700,
                fontSize = 24.sp
        ),
        h6 = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                lineHeight = 20.sp,
                letterSpacing = 3.sp
        ),
        subtitle1 = TextStyle(
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 3.sp
        ),
        subtitle2 = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = 0.1.em
        ),
        body1 = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = 0.1.em,
                color = Mustard
        ),
        body2 = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = 0.1.em,
                color = PurpleDark
        ),
        button = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                letterSpacing = 0.2.em
        ),
        caption = TextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 12.sp
        ),
        overline = TextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 10.sp
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