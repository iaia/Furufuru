package dev.iaiabot.furufuru.example.first

import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import dev.iaiabot.furufuru.feature.ui.issue.IssueBodyTemplate

@Composable
fun FirstContent(
    navController: NavController,
) {
    val observer = remember {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    Log.d("koko", "first on_create")
                }
                Lifecycle.Event.ON_START -> {
                    Log.d("koko", "first on_start")
                }
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("koko", "first on_resume")
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("koko", "first on_pause")
                }
                Lifecycle.Event.ON_STOP -> {
                    Log.d("koko", "first on_stop")
                }
                Lifecycle.Event.ON_DESTROY -> {
                    Log.d("koko", "first on_destroy")
                }
                Lifecycle.Event.ON_ANY -> {
                }
            }
        }
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(Unit) {
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    val context = LocalContext.current

    MaterialTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "hello")
            Text(text = "hello")
            Text(text = "hello")
            Text(
                text = IssueBodyTemplate.createBody(
                    "",
                    "user body",
                    "image url",
                    "file url"
                )
            )
            Button(
                onClick = {
                    CustomTabsIntent.Builder()
                        .build()
                        .launchUrl(context, Uri.parse("https://example.com"))
                }
            ) {
                Text("oepn custom tabs")
            }
            Button(
                onClick = {
                    navController.navigate("second") {
                        popUpTo("first")
                        launchSingleTop = true
                    }
                }
            ) {
                Text(text = "go to second")
            }
        }
    }
}

@Preview
@Composable
fun TextPreview() {
    Text("テキスト")
}
