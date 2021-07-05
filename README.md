# Furufuru

Make an issue on github easily just by shaking.

## install

app/build.gradle

```
debugInstrumentation "dev.iaiabot.furufuru:furufuru:${Versions.furufuru}"
```

## setup

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Furufuru.Builder(
            this,
        ).settingGithub(
            BuildConfig.GITHUB_API_TOKEN,
            "your name",
            "repository name"
        ).setLabels(
            "bug", "documentation"
        ).build()
    }
}
```

## LICENSE

MIT
