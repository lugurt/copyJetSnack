package com.example.copy

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.copy.model.SnackbarManager
import com.example.copy.ui.theme.CopyTheme
import com.example.copy.ui.theme.Ocean10
import com.google.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun CopyApp() {
  ProvideWindowInsets {
    SideEffect {
      Log.i("lanwq","compose window Insets")
    }
    CopyTheme {
      val appState = rememberCopyAppState()
      SideEffect {
        Log.i("lanwq","compose app theme")
      }
      Scaffold(
        scaffoldState = appState.scaffoldState,
        floatingActionButton = {
          CopyFloaActionButton()
        },
        bottomBar = {

        }

      ) {
        Column(
          verticalArrangement = Arrangement.Bottom,
          modifier = Modifier
            .fillMaxHeight()
            .padding(it)

        ) {
          Text(
            text = "Scaffold", modifier = Modifier
              .background
                (color = Color.Gray)
          )
          Text(
            text = "111", modifier = Modifier
              .background
                (color = Color.Gray)
          )
        }
      }
    }
  }
}


@Preview()
@Composable
fun PreviewApp() {
  CopyApp()
}

@Composable
fun CopyTopBar() {
  Surface(color = Ocean10) {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
      Text(text = "11231", textAlign = TextAlign.Center, style = MaterialTheme.typography.h1)
    }
  }
}

@Composable
fun CopyFloaActionButton(
  scope: CoroutineScope = rememberCoroutineScope()
) {
  Button(onClick = {
    scope.launch {
      SnackbarManager.showMessage(R.string.add_to_cart)
    }

  }) {
    Icon(imageVector = Icons.Filled.Email, contentDescription = "")
  }
}


