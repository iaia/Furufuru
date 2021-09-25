package dev.iaiabot.furufuru.feature.ui.issue

import android.os.Build
import dev.iaiabot.furufuru.BuildConfig
import dev.iaiabot.furufuru.feature.Furufuru

// issue repositoryにもっていきたい
object IssueBodyTemplate {
    private const val USER_NAME = "USER_NAME"
    private const val USERS_BODY = "USERS_BODY"
    private const val APP_NAME = "APP_NAME"
    private const val APP_VERSION = "APP_VERSION"
    private const val DEVICE_VERSION = "DEVICE_VERSION"
    private const val DEVICE_OS = "DEVICE_OS"
    private const val FURUFURU_VERSION_NAME = "FURUFURU_VERSION_NAME"
    private const val FURUFURU_VERSION_CODE = "FURUFURU_VERSION_CODE"
    private const val IMAGE_URL = "IMAGE_URL"
    private const val IMAGE_FILE_URL = "IMAGE_FILE_URL"

    private const val TEMPLATE = """
## body

$USERS_BODY

## screenshot

[screenshot]($IMAGE_FILE_URL)

<img src="$IMAGE_URL" alt="look above" width=300px />

## device info

|key|value|
|:--:|:--:|
|User Name|$USER_NAME|
|App Name|$APP_NAME|
|App Version|$APP_VERSION|
|Device|$DEVICE_VERSION|
|Device OS|$DEVICE_OS|
|Furufuru Version|$FURUFURU_VERSION_NAME ($FURUFURU_VERSION_CODE)|
"""

    fun createBody(
        userName: String,
        usersBody: String,
        imageUrl: String?,
        fileUrl: String?
    ): String {

        var body = TEMPLATE

        body = if (userName.isEmpty()) {
            body.replace(USER_NAME, "[Spartacus](https://youtu.be/FKCmyiljKo0?t=65)")
        } else {
            body.replace(USER_NAME, userName)
        }
        body = body.replace(USERS_BODY, usersBody)

        imageUrl?.let {
            body = body.replace(IMAGE_URL, it)
        }

        fileUrl?.let {
            body = body.replace(IMAGE_FILE_URL, it)
        }

        body = body.replace(DEVICE_VERSION, Build.MANUFACTURER + " " + Build.MODEL)
        body = body.replace(DEVICE_OS, Build.VERSION.RELEASE)
        body = body.replace(FURUFURU_VERSION_NAME, BuildConfig.VERSION_NAME)
        body = body.replace(FURUFURU_VERSION_CODE, BuildConfig.VERSION_CODE.toString())

        Furufuru.getApplicationName()?.let {
            body = body.replace(APP_NAME, it)
        }

        Furufuru.getAppVersionName()?.let {
            body = body.replace(APP_VERSION, it)
        }

        return body
    }
}
