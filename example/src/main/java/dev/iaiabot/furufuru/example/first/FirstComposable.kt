package dev.iaiabot.furufuru.example.first

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import dev.iaiabot.furufuru.feature.ui.issue.IssueBodyTemplate

@Composable
fun FirstContent(
    navController: NavController,
    openCustomTabs: () -> Unit = {}
) {
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
                onClick = openCustomTabs
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

/*
private fun openCustomTabs() {
    val url = "http://example.com"
    startActivity(
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
    )
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent: CustomTabsIntent = builder.build()
    customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
}
 */
