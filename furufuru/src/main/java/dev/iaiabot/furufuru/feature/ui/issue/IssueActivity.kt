package dev.iaiabot.furufuru.feature.ui.issue

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dev.iaiabot.furufuru.feature.Furufuru
import org.koin.androidx.viewmodel.ext.android.viewModel


internal class IssueActivity : AppCompatActivity() {
    companion object {
        fun createIntent(
            context: Context
        ) = Intent(context, IssueActivity::class.java)
    }

    private val model by viewModel<IssueViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Furufuru.takeScreenshot()
        setContent {
            IssueContent(model)
        }

        model.command.observe(this) {
            when (it) {
                is Command.Finish -> {
                    Toast.makeText(this, "posted!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Command.ShowFilePath -> {
                    // Toast.makeText(this, "filePath: ${it.filePath}", android.widget.Toast.LENGTH_LONG).show()
                }
                is Command.Error -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
