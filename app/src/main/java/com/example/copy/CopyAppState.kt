package com.example.copy

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
  scaffoldState: ScaffoldState = rememberScaffoldState(), navController: NavHostController =
    rememberNavController(),
  snackManager: SnackbarManager = SnackbarManager, resource: Resources = resources(), scope:
  CoroutineScope = rememberCoroutineScope()
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


@Composable
@ReadOnlyComposable
fun resources(): Resources {
  LocalContext.current
  return LocalContext.current.resources
}