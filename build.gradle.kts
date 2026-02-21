import plugins.unitTesting

plugins {
    id("com.bluecoins.plugins.base-library")
    kotlin("plugin.serialization")
}

private val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    // modules
    unitTesting(libs)
}
