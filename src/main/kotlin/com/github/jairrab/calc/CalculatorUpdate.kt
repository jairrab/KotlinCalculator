package com.github.jairrab.calc

sealed class CalculatorUpdate {
    object Initializing : CalculatorUpdate()

    class OnUpdate(
        val key: String?,
        val entries: List<String>,
        val result: Double
    ) : CalculatorUpdate()

    class InvalidKey(val invalidKeyType: InvalidKeyType) : CalculatorUpdate()
}