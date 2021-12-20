package com.example.copy.ui.page.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.copy.ui.theme.Ocean10

@Composable
fun CopyBottomBar() {
  Surface(color = Ocean10, shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)) {
    Box(
      modifier = Modifier
        .height(48.dp)
        .fillMaxWidth()
    ) {
    }
  }

}