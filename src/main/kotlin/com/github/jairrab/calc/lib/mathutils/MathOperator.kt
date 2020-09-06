/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.mathutils

const val DECIMAL = "."

/** Available math operators  */
internal enum class Operator(val symbol: String) {
    Plus("+"),
    Minus("-"),
    Divide("/"),
    Multiply("*"),
    Equals("="),
}

object OperatorUtils {
    private val operators = Operator.values().map { it.symbol }

    internal fun getOperatorSymbol(operator: Operator): String {
        return when (operator) {
            Operator.Plus -> Operator.Plus.symbol
            Operator.Minus -> Operator.Minus.symbol
            Operator.Divide -> Operator.Divide.symbol
            Operator.Multiply -> Operator.Multiply.symbol
            Operator.Equals -> Operator.Equals.symbol
        }
    }

    internal fun getOperator(entry: String): Operator {
        return when (entry) {
            Operator.Plus.symbol -> Operator.Plus
            Operator.Minus.symbol -> Operator.Minus
            Operator.Divide.symbol -> Operator.Divide
            Operator.Multiply.symbol -> Operator.Multiply
            Operator.Equals.symbol -> Operator.Equals
            else -> throw IllegalStateException("Invalid operator entry")
        }
    }

    fun getOperators(list: List<String>): List<String> {
        return list.filter { operators.contains(it) }
    }

    fun isOperator(entry: String): Boolean {
        return (operators.contains(entry))
    }

    fun isNumber(entry: String): Boolean {
        return entry.toIntOrNull() != null
    }
}