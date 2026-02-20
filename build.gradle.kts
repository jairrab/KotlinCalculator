import config.unitTesting

plugins {
    id("base-library")
    kotlin("plugin.serialization")
}

private val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    // modules
    unitTesting(libs)
}
