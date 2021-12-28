package com.example.copy.ui.page.home

import android.util.Log
import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.copy.R
import com.example.copy.resources
import com.example.copy.ui.theme.Ocean3
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

enum class HomeSections(
  @StringRes val title: Int,
  val icon: ImageVector,
  val route: String
) {
  FEED(R.string.home_feed, Icons.Outlined.Home, "home/feed"),
  SEARCH(R.string.home_search, Icons.Outlined.Search, "home/search"),
  CART(R.string.home_cart, Icons.Outlined.ShoppingCart, "home/cart"),
  PROFILE(R.string.home_profile, Icons.Outlined.AccountCircle, "home/profile")
}


@Composable
fun CopyBottomBar() {
}


@Composable
fun BottomBarItem(
  modifier: Modifier = Modifier, @FloatRange(from = 0.0, to = 1.0) progress: Float
  = 1.0f
) {
  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
  ) {
    Layout(
      content = {
        Box(
          modifier = Modifier
            .layoutId("icon")
            .padding(2.dp)
        ) {
          Icon(imageVector = HomeSections.FEED.icon, contentDescription = "")
        }
        val scale = lerp(0.6f, 1.0f, progress.toFloat())
        Box(modifier = Modifier
          .layoutId("text")
          .padding(2.dp)
          .graphicsLayer {
            scaleX = scale
            scaleY = scale
            alpha = progress.toFloat()
            transformOrigin = TransformOrigin(0.0f, 0.5f)
          }) {
          Text(text = stringResource(id = HomeSections.FEED.title).uppercase())
        }
      }) { measurables, constraints ->
      val iconPlaceable =
        measurables.first { it.layoutId == "icon" }.measure(constraints = constraints)
      val textPlaceable =
        measurables.first { it.layoutId == "text" }.measure(constraints = constraints)
      val iconY = (constraints.maxHeight - iconPlaceable.height) / 2
      val textY = (constraints.maxHeight - textPlaceable.height) / 2
      val textWidth = (textPlaceable.width * progress).toInt()
      val iconX = (constraints.maxWidth - iconPlaceable.width - textWidth) / 2

      val textX = iconX + iconPlaceable.width
      layout(constraints.maxWidth, constraints.maxHeight) {
        iconPlaceable.placeRelative(iconX, iconY, 0f)
        if (progress > 0) {
          textPlaceable.placeRelative(textX, textY)
        }
      }
    }
  }


}

@Composable
fun BottomNavLayout(
  itemCount: Int, selectedIndex: Int, indicator: @Composable BoxScope.() -> Unit,
  modifier: Modifier = Modifier, content: @Composable () -> Unit, animSpec: AnimationSpec<Float>,

  ) {
  val selectionFractions = remember(key1 = itemCount) {
    List(itemCount) { index ->
      Animatable(if (selectedIndex == index) 1f else 0f)
    }
  }
  selectionFractions.forEachIndexed { index, animatable ->
    val target = if (index == selectedIndex) 1f else 0f
    LaunchedEffect(target, animSpec) {
      animatable.animateTo(targetValue = target, animationSpec = animSpec)
    }
  }
  val indicatorIndex = remember {
    Animatable(0f)
  }
  val targetIndicatorIndex = selectedIndex.toFloat()
  LaunchedEffect(targetIndicatorIndex) {
    indicatorIndex.animateTo(targetIndicatorIndex, animationSpec = animSpec)
  }
  Layout(
    modifier = modifier.height(56.dp),
    content = {
      content()
      Box(modifier = Modifier.layoutId("indicator"), content = indicator)
    }) { measurables, constraints ->
    check(itemCount + 1 == measurables.size)
    val indicatorMeasurable = measurables.first { it.layoutId == "indicator" }
    val itemWidth = constraints.maxWidth / (itemCount + 1)
    val selectedWidth = itemWidth * 2
    val indicatorPlaceable = indicatorMeasurable.measure(
      constraints = constraints.copy(
        maxWidth = itemWidth,
        minWidth = itemWidth
      )
    )
    val itemPlaceables = measurables.filterNot { it.layoutId == "indicator" }.mapIndexed { index,
      measurable ->
      val width = lerp(itemWidth, selectedWidth, selectionFractions[index].value)
      measurable.measure(constraints.copy(maxWidth = width, minWidth = width))
    }
    layout(
      constraints.maxWidth, height = itemPlaceables.maxByOrNull { it.height }?.height ?: 0
    ) {
      val indicatorLeft = itemWidth * indicatorIndex.value
      indicatorPlaceable.placeRelative(indicatorLeft.toInt(), 0, 0f)
      var x = 0
      itemPlaceables.forEach { placeable ->
        placeable.placeRelative(x, 0)
        x += placeable.width
      }
    }
  }
}


@Preview
@Composable
fun CopyBottomItem() {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    var progress = getProgress().value.toFloat() / 1000f
    Surface(color = Ocean3) {
      BottomBarItem(modifier = Modifier.size(100.dp, 40.dp), progress)
    }
    Slider(value = progress, onValueChange = { slider -> progress = slider })
  }
}

@Composable
fun getProgress(): State<Int> {
  val resource = resources()
  return produceState(initialValue = 0) {
    val flow = flow<Int> {
      delay(1000)
      for (i in 0..1000) {
        delay(1)
        emit(i)
      }
    }.onCompletion {
      Log.i("lanwq", "complete")
    }
    val sing = flow.takeUntilSignal()
    sing.collect {
      value = it
    }
    Log.i("lanwq", "finished")
  }
}


fun <T> Flow<T>.takeUntilSignal(signal: Flow<Unit>? = null): Flow<T> = flow {
  try {
    coroutineScope {
//      launch {
//        signal.take(1).collect()
//        println("signalled")
//        this@coroutineScope.cancel()
//      }

      collect {
        emit(it)
      }
    }

  } catch (e: CancellationException) {
    //ignore
  }
}

