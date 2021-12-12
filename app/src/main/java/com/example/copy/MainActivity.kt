package com.example.copy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.copy.ui.theme.CopyTheme
import com.example.copy.ui.theme.LocalJetsnackColors
import com.example.copy.ui.theme.Neutral0
import com.example.copy.ui.theme.Neutral8
import com.example.copy.ui.theme.ProvideJetSnackColors
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting(name = "Android")
        }
    }
}

@Composable
fun Greeting(name: String) {
    CopyTheme {
        // A surface container using the 'background' color from the theme
        val scope = rememberCoroutineScope()
        val colors = LocalJetsnackColors.current.copy()
        var backgroundColor by remember {
            mutableStateOf(
                Neutral0
            )
        }
        Surface(color = colors.uiBackground) {
            Column() {
                Surface(color = backgroundColor) {
                    Text(text = name)
                }
                ProvideJetSnackColors(colors = colors.copy().apply {
                    uiBackground = backgroundColor
                }) {
                    TextButton(onClick = {
                        scope.launch {
                            Log.d("lanwq", "backgroudColor:${backgroundColor.toString()}")
                            backgroundColor = Neutral8
                            Log.d("lanwq", "backgroudColor:${LocalJetsnackColors.toString()}")

                        }
                    }) {
                        Text(
                            text = "1111",
                            Modifier.background(color = LocalJetsnackColors.current.uiBackground)
                        )
                    }
                }
            }
        }
        SideEffect {
            Log.d("TAG", "backgroudColor:${backgroundColor.toString()}")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    CopyTheme {
        Greeting("Android")
    }
}