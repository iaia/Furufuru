package dev.iaiabot.furufuru.feature.ui.issue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun FurufuruScreen(
    viewModel: IssueViewModel
) {
    val title: String by viewModel.title.observeAsState("")
    val body: String by viewModel.body.observeAsState("")
    val userName: String by viewModel.userName.observeAsState("")
    val imageStrBase64: String? by viewModel.imageStrBase64.observeAsState("")
    val isProgress: Boolean by viewModel.nowSending.observeAsState(false)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    FurufuruTheme {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                Column(Modifier.fillMaxWidth()) {
                    FurufuruTextField(
                        text = title,
                        label = "issue title",
                        onChange = { viewModel.onTitleChange(it) },
                    )
                    FurufuruTextField(
                        text = body,
                        label = "body",
                        multiline = true,
                        onChange = { viewModel.onBodyChange(it) },
                    )
                    FurufuruTextField(
                        text = userName,
                        label = "user name",
                        onChange = { viewModel.onUserNameChange(it) },
                    )
                    IssueLabels()
                }
            },
            floatingActionButton = {
                SendButton(isProgress) { viewModel.post() }
            },
            sheetPeekHeight = 128.dp,
        ) {
            ImageContent(imageStrBase64)
        }
        Progress(isProgress)
    }
}

@Preview
@Composable
fun FurufuruScreenPreview() {
    FurufuruScreen(
        viewModel = FakeFurufuruViewModel()
    )
}

private class FakeFurufuruViewModel() : IssueViewModel() {
    override val title: LiveData<String> = MutableLiveData()
    override val body: LiveData<String> = MutableLiveData()
    override val userName: LiveData<String> = MutableLiveData()
    override val imageStrBase64: LiveData<String?> = MutableLiveData(null)
    override val labels: LiveData<List<String>> = MutableLiveData()
    override val command: LiveData<Command> = MutableLiveData()
    override val nowSending: LiveData<Boolean> = MutableLiveData()

    override fun onTitleChange(title: String) {
    }

    override fun onBodyChange(body: String) {
    }

    override fun onUserNameChange(userName: String) {
    }

    override fun onCheckedChangeLabel(isChecked: Boolean, label: String) {
    }

    override fun post() {
    }
}
