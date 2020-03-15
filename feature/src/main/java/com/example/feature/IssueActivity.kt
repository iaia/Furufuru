package com.example.feature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.example.data.di.apiModule
import com.example.data.di.repositoryModule
import com.example.feature.databinding.ActivityIssueBinding
import com.example.feature.di.viewModelModule
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import java.lang.Exception

class IssueActivity : AppCompatActivity() {
    init {
        try {
            startKoin {
                androidLogger()
                modules(
                    listOf(
                        viewModelModule,
                        apiModule,
                        repositoryModule
                    )
                )
            }
        } catch (e: Exception) {

        }
    }

    companion object {
        private const val ARG_FILE_PATH = "file_path"

        fun createIntent(
            context: Context,
            filePath: String?
        ) = Intent(context, IssueActivity::class.java).apply {
            putExtra(ARG_FILE_PATH, filePath)
        }
    }

    private val model by viewModel<IssueViewModel> { parametersOf(filePath) }
    private val binding by lazy { DataBindingUtil.setContentView<ActivityIssueBinding>(this, R.layout.activity_issue) }
    private val filePath by lazy { intent.extras?.getString(ARG_FILE_PATH) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.command.observe(this) {
            when(it) {
                is Command.Finish -> finish()
                is Command.ShowFilePath -> {
                    // Toast.makeText(this, "filePath: ${it.filePath}", android.widget.Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.viewModel = model
    }
}
