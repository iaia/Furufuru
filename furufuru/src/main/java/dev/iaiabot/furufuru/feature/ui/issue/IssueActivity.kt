package dev.iaiabot.furufuru.feature.ui.issue

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import dev.iaiabot.furufuru.feature.R
import dev.iaiabot.furufuru.feature.databinding.ActivityIssueBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class IssueActivity : AppCompatActivity() {


    companion object {
        fun createIntent(
            context: Context
        ) = Intent(context, IssueActivity::class.java).apply {
        }
    }

    private val model by viewModel<IssueViewModel>()
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityIssueBinding>(
            this,
            R.layout.activity_issue
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        lifecycle.addObserver(model)

        model.command.observe(this) {
            when (it) {
                is Command.Finish -> finish()
                is Command.ShowFilePath -> {
                    // Toast.makeText(this, "filePath: ${it.filePath}", android.widget.Toast.LENGTH_LONG).show()
                }
            }
        }
        binding.viewModel = model
        binding.lifecycleOwner = this
    }
}
