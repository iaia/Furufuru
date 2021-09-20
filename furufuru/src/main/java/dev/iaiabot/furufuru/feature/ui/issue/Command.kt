package dev.iaiabot.furufuru.feature.ui.issue

internal sealed class Command {
    object Finish : Command()
    object ShowFilePath : Command()
    class Error(val errorMessage: String) : Command()
}
