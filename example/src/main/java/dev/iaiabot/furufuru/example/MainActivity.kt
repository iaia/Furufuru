package dev.iaiabot.furufuru.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.iaiabot.furufuru.example.databinding.ActivityMainBinding
import dev.iaiabot.furufuru.feature.ui.issue.IssueBodyTemplate

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.tvHello.text = "hello"
        binding.tvIssueTemplate.text = IssueBodyTemplate.createBody(
            "user body",
            "image url",
            "file url"
        )

        setContentView(binding.root)
    }
}
