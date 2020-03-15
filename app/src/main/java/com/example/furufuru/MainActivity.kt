package com.example.furufuru

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.feature.IssueActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(IssueActivity.createIntent(this))
    }
}
