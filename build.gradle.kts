import config.unitTesting

plugins {
    id("base-library")
}

private val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    // modules
    unitTesting(libs)
}
