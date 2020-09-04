/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.mathutils

const val DECIMAL = "."

/** Avaiable math operators  */
internal enum class Operator(val symbol: String) {
    Plus("+"),
    Minus("-"),
    Divide("/"),
    Multiply("*"),
    Equals("="),
}

object OperatorUtils {
    internal fun getOperatorSymbol(operator: Operator): String {
        return when (operator) {
            Operator.Plus -> Operator.Plus.symbol
            Operator.Minus -> Operator.Minus.symbol
            Operator.Divide -> Operator.Divide.symbol
            Operator.Multiply -> Operator.Multiply.symbol
            Operator.Equals -> Operator.Equals.symbol
        }
    }

    fun getOperators(list: List<String>): List<String> {
        val symbols = Operator.values().map { it.symbol }
        return list.filter { symbols.contains(it) }
    }
}