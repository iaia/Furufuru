package dev.iaiabot.furufuru.example.second

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import dev.iaiabot.furufuru.feature.ui.issue.IssueBodyTemplate

@Composable
fun SecondContent(
    navController: NavController,
) {
    MaterialTheme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "second")
            Text(text = "second")
            Text(text = "second")
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
                    navController.navigate("first")
                }
            ) {
                Text(text = "go to first")
            }
        }
    }
}
