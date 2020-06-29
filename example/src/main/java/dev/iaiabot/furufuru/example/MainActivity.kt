package dev.iaiabot.furufuru.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
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
        binding.btOpenWebview.setOnClickListener {
            val url = "http://example.com"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent: CustomTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(url))
        }

        setContentView(binding.root)
    }
}
