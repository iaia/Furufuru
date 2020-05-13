# Furufuru

Make an issue on github easily just by shaking.

## install

project/build.gradle

```
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url  "https://dl.bintray.com/iaia/maven"
        }
    }
}
```

app/build.gradle

```
debugInstrumentation "dev.iaiabot.furufuru:furufuru:${Versions.furufuru}"
```

## setup

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Furufuru.builder(
            this,
            BuildConfig.GITHUB_API_TOKEN,
            "iaia",
            "Furufuru"
        ).build()
    }
}
```

## LICENSE

MIT
