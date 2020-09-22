/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.mathutils

import com.github.jairrab.calc.CalculatorButton

object OperatorUtils {
    private val operators = listOf(
        CalculatorButton.PLUS,
        CalculatorButton.MINUS,
        CalculatorButton.DIVISION,
        CalculatorButton.MULTIPLY,
        CalculatorButton.EQUALS,
    )

    val operatorTags = operators.map { it.tag }

    internal fun getOperator(entry: String): CalculatorButton {
        return when (entry) {
            CalculatorButton.PLUS.tag -> CalculatorButton.PLUS
            CalculatorButton.MINUS.tag -> CalculatorButton.MINUS
            CalculatorButton.DIVISION.tag -> CalculatorButton.DIVISION
            CalculatorButton.MULTIPLY.tag -> CalculatorButton.MULTIPLY
            CalculatorButton.EQUALS.tag -> CalculatorButton.EQUALS
            else -> throw IllegalStateException("Invalid operator entry")
        }
    }

    fun getOperators(list: List<String>): List<String> {
        return list.filter { operatorTags.contains(it) }
    }

    fun isOperator(entry: String): Boolean {
        return (operatorTags.contains(entry))
    }

    fun isNumber(entry: String): Boolean {
        return entry.toIntOrNull() != null
    }
}