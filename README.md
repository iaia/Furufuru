# Furufuru

keys.properties

```
GITHUB_API_TOKEN=aaa
GITHUB_REPOS_OWNER=iaia
GITHUB_REPOSITORY=Furufuru
FURUFURU_BRANCH=furufuru-image-branch
```


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
