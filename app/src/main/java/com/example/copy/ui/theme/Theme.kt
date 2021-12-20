package com.example.copy.ui.theme

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = JetsnackColors(
  brand = Shadow5,
  brandSecondary = Ocean3,
  uiBackground = Neutral0,
  uiBorder = Neutral4,
  uiFloated = FunctionalGrey,
  textSecondary = Neutral7,
  textHelp = Neutral6,
  textInteractive = Neutral0,
  textLink = Ocean11,
  iconSecondary = Neutral7,
  iconInteractive = Neutral0,
  iconInteractiveInactive = Neutral1,
  error = FunctionalRed,
  gradient6_1 = listOf(Shadow4, Ocean3, Shadow2, Ocean3, Shadow4),
  gradient6_2 = listOf(Rose4, Lavender3, Rose2, Lavender3, Rose4),
  gradient3_1 = listOf(Shadow2, Ocean3, Shadow4),
  gradient3_2 = listOf(Rose2, Lavender3, Rose4),
  gradient2_1 = listOf(Shadow4, Shadow11),
  gradient2_2 = listOf(Ocean3, Shadow3),
  gradient2_3 = listOf(Lavender3, Rose2),
  tornado1 = listOf(Shadow4, Ocean3),
  isDark = false
)

private val DarkColorPalette = JetsnackColors(
  brand = Shadow1,
  brandSecondary = Ocean2,
  uiBackground = Neutral8,
  uiBorder = Neutral3,
  uiFloated = FunctionalDarkGrey,
  textPrimary = Shadow1,
  textSecondary = Neutral0,
  textHelp = Neutral1,
  textInteractive = Neutral7,
  textLink = Ocean2,
  iconPrimary = Shadow1,
  iconSecondary = Neutral0,
  iconInteractive = Neutral7,
  iconInteractiveInactive = Neutral6,
  error = FunctionalRedDark,
  gradient6_1 = listOf(Shadow5, Ocean7, Shadow9, Ocean7, Shadow5),
  gradient6_2 = listOf(Rose11, Lavender7, Rose8, Lavender7, Rose11),
  gradient3_1 = listOf(Shadow9, Ocean7, Shadow5),
  gradient3_2 = listOf(Rose8, Lavender7, Rose11),
  gradient2_1 = listOf(Ocean3, Shadow3),
  gradient2_2 = listOf(Ocean4, Shadow2),
  gradient2_3 = listOf(Lavender3, Rose3),
  tornado1 = listOf(Shadow4, Ocean3),
  isDark = true
)


@Composable
fun CopyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }
  val systemUiController = rememberSystemUiController()
  SideEffect {
//    systemUiController.setSystemBarsColor(color = colors.uiBackground.copy(AlphaNearOpaque))
    Log.i("lanwq","change ")
    systemUiController.setSystemBarsColor(color = Ocean10)
  }
  ProvideJetSnackColors(colors = colors) {
    MaterialTheme(
      typography = Typography,
      shapes = Shapes,
      content = content
    )
  }
}

object CopyTheme {
  val colors: JetsnackColors
    @Composable
    get() = LocalJetsnackColors.current
}