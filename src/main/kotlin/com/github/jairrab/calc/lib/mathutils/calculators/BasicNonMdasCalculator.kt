/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.mathutils.calculators

import com.github.jairrab.calc.CalculatorButton
import com.github.jairrab.calc.lib.mathutils.EntriesCalculator
import com.github.jairrab.calc.lib.mathutils.OperatorUtils

class BasicNonMdasCalculator : EntriesCalculator {
    override fun solve(entries: List<String>): Double {
        return scan(entries)
    }

    private fun scan(entries: List<String>): Double {
        var result = 0.0
        var lastOperator = CalculatorButton.PLUS
        for (entry in entries) {
            when {
                OperatorUtils.isNumber(entry) -> {
                    result = when (lastOperator) {
                        CalculatorButton.PLUS -> result + entry.toInt()
                        CalculatorButton.MINUS -> result - entry.toInt()
                        CalculatorButton.DIVISION -> result / entry.toInt()
                        CalculatorButton.MULTIPLY -> result * entry.toInt()
                        else -> throw IllegalStateException("Invalid last operator")
                    }
                }
                OperatorUtils.isOperator(entry) -> {
                    lastOperator = OperatorUtils.getOperator(entry)
                }
                else -> throw IllegalStateException("Invalid set of entries")
            }
        }
        return result
    }
}