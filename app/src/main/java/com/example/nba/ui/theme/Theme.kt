package com.example.nba.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(

    primary = NBAOrange,
    secondary = NBABlue,
    tertiary = NBARed,

    background = DarkBackground,
    surface = CardDark,

    onPrimary = TextLight,
    onSecondary = TextLight,
    onTertiary = TextLight,

    onBackground = TextLight,
    onSurface = TextLight

)

private val LightColorScheme = lightColorScheme(

    primary = NBAOrange,
    secondary = NBABlue,
    tertiary = NBARed,

    background = LightBackground,
    surface = CardColor,

    onPrimary = TextLight,
    onSecondary = TextLight,
    onTertiary = TextLight,

    onBackground = TextDark,
    onSurface = TextDark

)

@Composable
fun NBATheme(

    darkTheme: Boolean = isSystemInDarkTheme(),

    content: @Composable () -> Unit

) {

    MaterialTheme(

        colorScheme = if (darkTheme)
            DarkColorScheme
        else
            LightColorScheme,

        typography = Typography,

        content = content

    )

}