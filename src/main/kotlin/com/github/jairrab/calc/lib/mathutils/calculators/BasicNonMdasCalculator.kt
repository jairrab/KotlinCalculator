@file:Suppress(
    "CyclomaticComplexMethod",
    "SwallowedException",
    "ThrowsCount",
    "UseCheckOrError",
    "LoopWithTooManyJumpStatements",
)

/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.mathutils.calculators

import com.github.jairrab.calc.CalculatorButton.DIVISION
import com.github.jairrab.calc.CalculatorButton.MINUS
import com.github.jairrab.calc.CalculatorButton.MULTIPLY
import com.github.jairrab.calc.CalculatorButton.PERCENT
import com.github.jairrab.calc.CalculatorButton.PLUS
import com.github.jairrab.calc.lib.mathutils.DivideByZeroException
import com.github.jairrab.calc.lib.mathutils.EntriesCalculator
import com.github.jairrab.calc.lib.mathutils.OperatorUtils.isOperator
import com.github.jairrab.calc.lib.utils.trimEndChar
import java.math.BigDecimal
import java.math.RoundingMode

class BasicNonMdasCalculator : EntriesCalculator {
    override fun solve(entries: List<String>): BigDecimal {
        val entriesToProcess = entries.toMutableList().apply {
            if (isOperator(last())) removeLast()
        }

        return calculate(entriesToProcess)
    }

    private fun calculate(entries: List<String>): BigDecimal {
        var a = BigDecimal.ZERO

        if (entries.size == 1) {
            return getEntryWithPercentFactor(entries[0])
        }

        for (i in entries.indices) {
            if (isOperator(entries[i])) continue

            if (i == 0) {
                a = when (entries[i + 1]) {
                    PLUS.tag, MINUS.tag -> getEntryWithPercentFactor(entries[0])
                    MULTIPLY.tag, DIVISION.tag -> getEntryWithPercentFactor(entries[0])
                    else -> throw IllegalStateException("Error solving first entry")
                }
                continue
            }

            if (i == entries.lastIndex) {
                a = when (entries[i - 1]) {
                    PLUS.tag -> {
                        a.add(getEntryWithPercentFactor(entries[i], a))
                    }
                    MINUS.tag -> {
                        a.minus(getEntryWithPercentFactor(entries[i], a))
                    }
                    MULTIPLY.tag -> {
                        a.multiply(getEntryWithPercentFactor(entries[i]))
                    }
                    DIVISION.tag -> {
                        a.divide(getDivisor(entries, i), 10, RoundingMode.HALF_UP)
                    }
                    else -> throw IllegalStateException("Error solving last entry")
                }
                return a
            }

            when (entries[i + 1]) {
                PLUS.tag -> a = when (entries[i - 1]) {
                    PLUS.tag -> {
                        a.add(getEntryWithPercentFactor(entries[i], a))
                    }
                    MINUS.tag -> {
                        a.minus(getEntryWithPercentFactor(entries[i], a))
                    }
                    MULTIPLY.tag -> {
                        a.multiply(getEntryWithPercentFactor(entries[i]))
                    }
                    DIVISION.tag -> {
                        a.divide(getDivisor(entries, i), 10, RoundingMode.HALF_UP)
                    }
                    else -> throw IllegalStateException("Error checking plus tag")
                }
                MINUS.tag -> a = when (entries[i - 1]) {
                    PLUS.tag -> {
                        a.add(getEntryWithPercentFactor(entries[i], a))
                    }
                    MINUS.tag -> {
                        a.minus(getEntryWithPercentFactor(entries[i], a))
                    }
                    MULTIPLY.tag -> {
                        a.multiply(getEntryWithPercentFactor(entries[i]))
                    }
                    DIVISION.tag -> {
                        a.divide(getDivisor(entries, i), 10, RoundingMode.HALF_UP)
                    }
                    else -> throw IllegalStateException("Error checking minus tag")
                }
                MULTIPLY.tag -> a = when (entries[i - 1]) {
                    PLUS.tag -> {
                        a.add(getEntryWithPercentFactor(entries[i]))
                    }
                    MINUS.tag -> {
                        a.minus(getEntryWithPercentFactor(entries[i]))
                    }
                    MULTIPLY.tag -> {
                        a.multiply(getEntryWithPercentFactor(entries[i]))
                    }
                    DIVISION.tag -> {
                        a.divide(getDivisor(entries, i), 10, RoundingMode.HALF_UP)
                    }
                    else -> throw IllegalStateException("Error checking multiply tag")
                }
                DIVISION.tag -> a = when (entries[i - 1]) {
                    PLUS.tag -> {
                        a.add(getEntryWithPercentFactor(entries[i]))
                    }
                    MINUS.tag -> {
                        a.minus(getEntryWithPercentFactor(entries[i]))
                    }
                    MULTIPLY.tag -> {
                        a.multiply(getEntryWithPercentFactor(entries[i]))
                    }
                    DIVISION.tag -> {
                        a.divide(getDivisor(entries, i), 10, RoundingMode.HALF_UP)
                    }
                    else -> throw IllegalStateException("Error checking division tag")
                }
                else -> throw IllegalStateException("Error checking next operator tag")
            }
        }
        throw IllegalStateException("Error solving equation")
    }

    private fun getDivisor(entries: List<String>, i: Int): BigDecimal {
        val divisor = getEntryWithPercentFactor(entries[i])
        if (divisor == BigDecimal.ZERO) throw DivideByZeroException()
        return divisor
    }

    private fun getEntryWithPercentFactor(entry: String, baseNumber: BigDecimal): BigDecimal {
        return if (entry.endsWith(PERCENT.tag)) {
            baseNumber * BigDecimal(entry.trimEndChar()) / BigDecimal(100.0)
        } else {
            BigDecimal(entry)
        }
    }

    private fun getEntryWithPercentFactor(entry: String): BigDecimal {
        return if (entry.endsWith(PERCENT.tag)) {
            BigDecimal(entry.trimEndChar()) / BigDecimal(100.0)
        } else {
            BigDecimal(entry)
        }
    }
}
