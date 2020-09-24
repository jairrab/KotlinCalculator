package com.github.jairrab.calc

sealed class CalculatorUpdate {
    class Initializing(val number: Double) : CalculatorUpdate()

    class OnUpdate(
        val key: String?,
        val entries: List<String>,
        val result: Double
    ) : CalculatorUpdate()

    class InvalidKey(val invalidKeyType: InvalidKeyType) : CalculatorUpdate()

    sealed class Error : CalculatorUpdate() {
        class DivideByZero(val key: String?, val entries: List<String>) : Error()
    }
}