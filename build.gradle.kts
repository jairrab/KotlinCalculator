plugins {
    id("plugins.library")
}

private val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    // modules
    unitTesting(libs)
}
