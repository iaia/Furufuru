package dev.iaiabot.furufuru.example.second

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import dev.iaiabot.furufuru.feature.ui.issue.IssueBodyTemplate

@Composable
@Preview
fun SecondContent(
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
        }
    }
}
