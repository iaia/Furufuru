package dev.iaiabot.furufuru.example.first

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import dev.iaiabot.furufuru.feature.ui.issue.IssueBodyTemplate

@Composable
@Preview
fun FirstContent(
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
        }
    }
}
