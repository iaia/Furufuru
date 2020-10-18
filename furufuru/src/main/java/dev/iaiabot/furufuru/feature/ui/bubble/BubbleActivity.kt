package dev.iaiabot.furufuru.feature.ui.bubble

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.iaiabot.furufuru.feature.R

class BubbleActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context) =
            Intent(context, BubbleActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubble)
    }
}
