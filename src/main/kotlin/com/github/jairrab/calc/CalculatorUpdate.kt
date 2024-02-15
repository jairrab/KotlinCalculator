package com.github.jairrab.calc

import java.math.BigDecimal

sealed class CalculatorUpdate {
    class Initializing(val number: Double, val entries: List<String>) : CalculatorUpdate()

    class OnUpdate(
        val key: String?,
        val entries: List<String>,
        val result: BigDecimal,
        val resultText: String,
    ) : CalculatorUpdate()

    sealed class Error : CalculatorUpdate() {
        class DivideByZero(val key: String?, val entries: List<String>) : Error()

        class InvalidKey(val invalidKeyType: InvalidKeyType, val entries: List<String>) : Error()
    }
}
