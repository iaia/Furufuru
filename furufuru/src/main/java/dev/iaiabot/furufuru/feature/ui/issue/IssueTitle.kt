package dev.iaiabot.furufuru.feature.ui.issue

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun IssueTitle() {
    var title by remember {
        mutableStateOf("")
    }

    TextField(
        value = title,
        onValueChange = {
            title = it
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("title") },
        singleLine = true,
    )
}
