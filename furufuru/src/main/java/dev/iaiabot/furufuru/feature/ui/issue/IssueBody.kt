package dev.iaiabot.furufuru.feature.ui.issue

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun IssueBody() {
    var body by remember {
        mutableStateOf("")
    }

    TextField(
        value = body,
        onValueChange = {
            body = it
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("body") },
    )
}
