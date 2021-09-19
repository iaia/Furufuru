package dev.iaiabot.furufuru.feature.ui.issue

import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun IssueBody() {
    TextField(value = "body", onValueChange = {})
}
