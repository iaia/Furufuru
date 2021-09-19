package dev.iaiabot.furufuru.feature.ui.issue

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.Menu
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import dev.iaiabot.furufuru.R
import dev.iaiabot.furufuru.feature.Furufuru
import org.koin.androidx.viewmodel.ext.android.viewModel


internal class IssueActivity : AppCompatActivity() {
    companion object {
        fun createIntent(
            context: Context
        ) = Intent(context, IssueActivity::class.java).apply {
        }
    }

    private val model by viewModel<IssueViewModel>()
    /*
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityIssueBinding>(
            this,
            R.layout.activity_issue
        )
    }
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Furufuru.takeScreenshot()
        setContent {
            IssueContent()
        }

        /*
        binding.lifecycleOwner = this
        lifecycle.addObserver(model)

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
        binding.viewModel = model
        binding.lifecycleOwner = this
         */

        //addLabelChips()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.issue_menu, menu)
        return true
    }

    /*
    private fun addLabelChips() {
        model.labels.observe(this) { labels ->
            val chips = labels.map { label ->
                Chip(this).apply {
                    text = label
                    isCheckable = true
                    setOnCheckedChangeListener { _, isChecked ->
                        model.onCheckedChangeLabel(isChecked, label)
                    }
                }
            }
            chips.forEach {
                binding.cgLabels.addView(it)
            }
        }
    }

     */
}

@Composable
@Preview
fun IssueContent() {
    FurufuruTheme {
        Column(Modifier.fillMaxWidth()) {
            IssueTitle()
            IssueBody()
            ImageCompose()
            // author name
            // image
            // send button
        }
    }
}

@Composable
@Preview
fun IssueTitle() {
    var title by remember {
        mutableStateOf("")
    }

    TextField(
        value = title,
        onValueChange = {
            title = it
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("title") },
        singleLine = true,
    )
}

@Composable
@Preview
fun IssueBody() {
    var body by remember {
        mutableStateOf("")
    }

    TextField(
        value = body,
        onValueChange = {
            body = it
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("body") },
    )
}

@Composable
@Preview
fun ImageCompose(
    fileStr: String = Base64ImageExample
) {
    val decodedString: ByteArray = Base64.decode(fileStr.trim(), Base64.DEFAULT)
    val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "",
        modifier = Modifier.fillMaxWidth()
    )
}

// TODO
private val Base64ImageExample = """
iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAApgAAAKYB3X3/OAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAANCSURBVEiJtZZPbBtFFMZ/M7ubXdtdb1xSFyeilBapySVU8h8OoFaooFSqiihIVIpQBKci6KEg9Q6H9kovIHoCIVQJJCKE1ENFjnAgcaSGC6rEnxBwA04Tx43t2FnvDAfjkNibxgHxnWb2e/u992bee7tCa00YFsffekFY+nUzFtjW0LrvjRXrCDIAaPLlW0nHL0SsZtVoaF98mLrx3pdhOqLtYPHChahZcYYO7KvPFxvRl5XPp1sN3adWiD1ZAqD6XYK1b/dvE5IWryTt2udLFedwc1+9kLp+vbbpoDh+6TklxBeAi9TL0taeWpdmZzQDry0AcO+jQ12RyohqqoYoo8RDwJrU+qXkjWtfi8Xxt58BdQuwQs9qC/afLwCw8tnQbqYAPsgxE1S6F3EAIXux2oQFKm0ihMsOF71dHYx+f3NND68ghCu1YIoePPQN1pGRABkJ6Bus96CutRZMydTl+TvuiRW1m3n0eDl0vRPcEysqdXn+jsQPsrHMquGeXEaY4Yk4wxWcY5V/9scqOMOVUFthatyTy8QyqwZ+kDURKoMWxNKr2EeqVKcTNOajqKoBgOE28U4tdQl5p5bwCw7BWquaZSzAPlwjlithJtp3pTImSqQRrb2Z8PHGigD4RZuNX6JYj6wj7O4TFLbCO/Mn/m8R+h6rYSUb3ekokRY6f/YukArN979jcW+V/S8g0eT/N3VN3kTqWbQ428m9/8k0P/1aIhF36PccEl6EhOcAUCrXKZXXWS3XKd2vc/TRBG9O5ELC17MmWubD2nKhUKZa26Ba2+D3P+4/MNCFwg59oWVeYhkzgN/JDR8deKBoD7Y+ljEjGZ0sosXVTvbc6RHirr2reNy1OXd6pJsQ+gqjk8VWFYmHrwBzW/n+uMPFiRwHB2I7ih8ciHFxIkd/3Omk5tCDV1t+2nNu5sxxpDFNx+huNhVT3/zMDz8usXC3ddaHBj1GHj/As08fwTS7Kt1HBTmyN29vdwAw+/wbwLVOJ3uAD1wi/dUH7Qei66PfyuRj4Ik9is+hglfbkbfR3cnZm7chlUWLdwmprtCohX4HUtlOcQjLYCu+fzGJH2QRKvP3UNz8bWk1qMxjGTOMThZ3kvgLI5AzFfo379UAAAAASUVORK5CYII=
"""
