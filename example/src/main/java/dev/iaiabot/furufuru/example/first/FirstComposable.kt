package dev.iaiabot.furufuru.example.first

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import dev.iaiabot.furufuru.feature.ui.issue.IssueBodyTemplate

@Composable
fun FirstContent(
    navController: NavController,
) {
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
