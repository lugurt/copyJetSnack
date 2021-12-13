package com.example.copy

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.copy.ui.page.home.CopyBottomBar
import com.example.copy.ui.theme.CopyTheme
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun CopyApp() {
  ProvideWindowInsets {
    CopyTheme {
      Scaffold(
        bottomBar = { CopyBottomBar() }
      ) {
        Text(text = "Scaffold")
      }
    }
  }
}


@Preview
@Composable
fun PreviewApp() {
    CopyApp()
}


