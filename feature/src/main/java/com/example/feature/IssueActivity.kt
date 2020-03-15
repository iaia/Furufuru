package com.example.feature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.data.di.apiModule
import com.example.data.di.repositoryModule
import com.example.feature.databinding.ActivityIssueBinding
import com.example.feature.di.viewModelModule
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class IssueActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, IssueActivity::class.java)
    }

    private val model by viewModel<IssueViewModel>()
    private val binding by lazy { DataBindingUtil.setContentView<ActivityIssueBinding>(this, R.layout.activity_issue) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        binding.viewModel = model
    }
}
