package com.example.feature.ui.issue

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.example.data.di.apiModule
import com.example.data.di.repositoryModule
import com.example.feature.R
import com.example.feature.databinding.ActivityIssueBinding
import com.example.feature.di.viewModelModule
import com.example.feature.service.SensorService
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf

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
        private const val ARG_FILE_STR = "file_str"

        fun createIntent(
            context: Context,
            fileStr: String?
        ) = Intent(context, IssueActivity::class.java).apply {
            putExtra(ARG_FILE_STR, fileStr)
        }
    }

    private val model by viewModel<IssueViewModel> {
        parametersOf(fileStr)
    }
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityIssueBinding>(
            this,
            R.layout.activity_issue
        )
    }
    private val fileStr by lazy { intent.extras?.getString(ARG_FILE_STR) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.command.observe(this) {
            when (it) {
                is Command.Finish -> finish()
                is Command.ShowFilePath -> {
                    // Toast.makeText(this, "filePath: ${it.filePath}", android.widget.Toast.LENGTH_LONG).show()
                }
            }
        }
        binding.viewModel = model
    }

    override fun onStop() {
        super.onStop()
        SensorService.startSensorEvent()
    }
}
