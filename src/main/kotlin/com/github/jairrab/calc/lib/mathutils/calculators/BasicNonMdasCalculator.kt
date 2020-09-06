/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.mathutils.calculators

import com.github.jairrab.calc.lib.mathutils.EntriesCalculator
import com.github.jairrab.calc.lib.mathutils.Operator.*
import com.github.jairrab.calc.lib.mathutils.OperatorUtils

class BasicNonMdasCalculator : EntriesCalculator {
    override fun solve(entries: List<String>): Double {
        return scan(entries)
    }

    private fun scan(entries: List<String>): Double {
        var result = 0.0
        var lastOperator = Plus
        for (entry in entries) {
            when {
                OperatorUtils.isNumber(entry) -> {
                    result = when (lastOperator) {
                        Plus -> result + entry.toInt()
                        Minus -> result - entry.toInt()
                        Divide -> result / entry.toInt()
                        Multiply -> result * entry.toInt()
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