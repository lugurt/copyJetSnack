package com.example.copy

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.copy.model.SnackbarManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Destinations used in the [CopyApp].
 */
object MainDestinations {
  const val HOME_ROUTE = "home"
  const val SNACK_DETAIL_ROUTE = "snack"
  const val SNACK_ID_KEY = "snackId"
}

@Composable
fun rememberCopyAppState(
  scaffoldState: ScaffoldState, navController: NavHostController,
  snackManager: SnackbarManager, resource: Resources, scope: CoroutineScope
) = remember(scaffoldState, navController, snackManager, resource, scope) {
  CopyAppState(
    scaffoldState = scaffoldState, navController = navController, resource =
    resource, snackManager = snackManager, scope = scope
  )
}


class CopyAppState(
  val scaffoldState: ScaffoldState, val navController: NavHostController, private
  val snackManager: SnackbarManager, private val resource: Resources, scope: CoroutineScope

) {
  init {
    scope.launch {
      snackManager.message.collect { messages ->
        if (messages.isNotEmpty()) {
          val message = messages[0]
          val showTxt = resource.getText(message.messageId)
          scaffoldState.snackbarHostState.showSnackbar(showTxt.toString())
          snackManager.setMessageShown(messageId = message.messageId)
        }
      }
    }

  }
}