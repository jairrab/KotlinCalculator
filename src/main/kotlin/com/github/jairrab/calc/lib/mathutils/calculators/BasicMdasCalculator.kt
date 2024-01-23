@file:Suppress(
    "CyclomaticComplexMethod",
    "ThrowsCount",
    "LoopWithTooManyJumpStatements",
    "UseCheckOrError",
)

/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.mathutils.calculators

import com.github.jairrab.calc.CalculatorButton.DECIMAL
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

class BasicMdasCalculator : EntriesCalculator {
    override fun solve(entries: List<String>): BigDecimal {
        val entriesToProcess = entries.toMutableList().apply {
            if (isOperator(last())) removeLast()
        }

        return calculate(entriesToProcess)
    }

    private fun calculate(entries: List<String>): BigDecimal {
        var a = BigDecimal.ZERO
        var b = BigDecimal.ZERO

        if (entries.size == 1) {
            return getEntryWithPercentFactor(entries[0])
        }

        for (i in entries.indices) {
            if (isOperator(entries[i])) continue

            if (i == 0) {
                when (entries[1]) {
                    PLUS.tag, MINUS.tag -> a = getEntryWithPercentFactor(entries[0])
                    MULTIPLY.tag, DIVISION.tag -> b = getEntryWithPercentFactor(entries[0])
                    else -> throw IllegalStateException("Error solving first entry")
                }
                continue
            }

            if (i == entries.lastIndex) {
                when (entries[i - 1]) {
                    PLUS.tag -> {
                        a = a.add(getEntryWithPercentFactor(entries[i], a))
                    }
                    MINUS.tag -> {
                        a = a.minus(getEntryWithPercentFactor(entries[i], a))
                    }
                    MULTIPLY.tag -> {
                        b = b.multiply(getEntryWithPercentFactor(entries[i]))
                        a = a.add(b)
                    }
                    DIVISION.tag -> {
                        b = b.divide(getDivisor(entries, i), 10, RoundingMode.HALF_UP)
                        a = a.add(b)
                    }
                    else -> throw IllegalStateException("Error solving last entry")
                }
                return a
            }

            when (entries[i + 1]) {
                PLUS.tag -> when (entries[i - 1]) {
                    PLUS.tag -> {
                        a = a.add(getEntryWithPercentFactor(entries[i], a))
                    }
                    MINUS.tag -> {
                        a = a.minus(getEntryWithPercentFactor(entries[i], a))
                    }
                    MULTIPLY.tag -> {
                        b = b.multiply(getEntryWithPercentFactor(entries[i]))
                        a = a.add(b)
                        b = BigDecimal.ZERO
                    }
                    DIVISION.tag -> {
                        b = b.divide(getDivisor(entries, i), 10, RoundingMode.HALF_UP)
                        a = a.add(b)
                        b = BigDecimal.ZERO
                    }
                    else -> throw IllegalStateException("Error checking plus tag")
                }
                MINUS.tag -> when (entries[i - 1]) {
                    PLUS.tag -> a += getEntryWithPercentFactor(entries[i], a)
                    MINUS.tag -> a -= getEntryWithPercentFactor(entries[i], a)
                    MULTIPLY.tag -> {
                        b = b.multiply(getEntryWithPercentFactor(entries[i]))
                        a = a.add(b)
                        b = BigDecimal.ZERO
                    }
                    DIVISION.tag -> {
                        b = b.divide(getDivisor(entries, i), 10, RoundingMode.HALF_UP)
                        a = a.add(b)
                        b = BigDecimal.ZERO
                    }
                    else -> throw IllegalStateException("Error checking minus tag")
                }
                MULTIPLY.tag -> b = when (entries[i - 1]) {
                    PLUS.tag -> getEntryWithPercentFactor(entries[i])
                    MINUS.tag -> -getEntryWithPercentFactor(entries[i])
                    MULTIPLY.tag -> {
                        b.multiply(getEntryWithPercentFactor(entries[i]))
                    }
                    DIVISION.tag -> {
                        b.divide(getDivisor(entries, i), 10, RoundingMode.HALF_UP)
                    }
                    else -> throw IllegalStateException("Error checking multiply tag")
                }
                DIVISION.tag -> b = when (entries[i - 1]) {
                    PLUS.tag -> getEntryWithPercentFactor(entries[i])
                    MINUS.tag -> -getEntryWithPercentFactor(entries[i])
                    MULTIPLY.tag -> {
                        b.multiply(getEntryWithPercentFactor(entries[i]))
                    }
                    DIVISION.tag -> {
                        b.divide(getDivisor(entries, i), 10, RoundingMode.HALF_UP)
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
        return when {
            entry == DECIMAL.tag -> BigDecimal.ZERO
            entry.endsWith(PERCENT.tag) ->
                (baseNumber * BigDecimal(entry.trimEndChar())) / BigDecimal(100.0)
            else -> BigDecimal(entry)
        }
    }

    private fun getEntryWithPercentFactor(entry: String): BigDecimal {
        return when {
            entry == DECIMAL.tag -> BigDecimal.ONE
            entry.endsWith(PERCENT.tag) -> BigDecimal(entry.trimEndChar()) / BigDecimal(100.0)
            else -> BigDecimal(entry)
        }
    }
}
