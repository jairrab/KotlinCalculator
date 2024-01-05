@file:Suppress(
    "CyclomaticComplexMethod",
    "ThrowsCount",
    "LoopWithTooManyJumpStatements",
    "UseCheckOrError"
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

class BasicMdasCalculator : EntriesCalculator {
    override fun solve(entries: List<String>): Double {
        val entriesToProcess = entries.toMutableList().apply {
            if (isOperator(last())) removeLast()
        }

        return calculate(entriesToProcess)
    }

    private fun calculate(entries: List<String>): Double {
        var a = 0.0
        var b = 0.0

        if (entries.size == 1) {
            return getEntryWithPercentFactor(entries[0])
        }

        for (i in entries.indices) {
            if (isOperator(entries[i])) continue

            if (i == 0) {
                when (entries[i + 1]) {
                    PLUS.tag, MINUS.tag -> a = getEntryWithPercentFactor(entries[0])
                    MULTIPLY.tag, DIVISION.tag -> b = getEntryWithPercentFactor(entries[0])
                    else -> throw IllegalStateException("Error solving first entry")
                }
                continue
            }

            if (i == entries.lastIndex) {
                when (entries[i - 1]) {
                    PLUS.tag -> a += getEntryWithPercentFactor(entries[i], a)
                    MINUS.tag -> a -= getEntryWithPercentFactor(entries[i], a)
                    MULTIPLY.tag -> {
                        b *= getEntryWithPercentFactor(entries[i])
                        a += b
                    }
                    DIVISION.tag -> {
                        b /= getDivisor(entries, i)
                        a += b
                    }
                    else -> throw IllegalStateException("Error solving last entry")
                }
                return a
            }

            when (entries[i + 1]) {
                PLUS.tag -> when (entries[i - 1]) {
                    PLUS.tag -> a += getEntryWithPercentFactor(entries[i], a)
                    MINUS.tag -> a -= getEntryWithPercentFactor(entries[i], a)
                    MULTIPLY.tag -> {
                        b *= getEntryWithPercentFactor(entries[i])
                        a += b
                        b = 0.0
                    }
                    DIVISION.tag -> {
                        b /= getDivisor(entries, i)
                        a += b
                        b = 0.0
                    }
                    else -> throw IllegalStateException("Error checking plus tag")
                }
                MINUS.tag -> when (entries[i - 1]) {
                    PLUS.tag -> a += getEntryWithPercentFactor(entries[i], a)
                    MINUS.tag -> a -= getEntryWithPercentFactor(entries[i], a)
                    MULTIPLY.tag -> {
                        b *= getEntryWithPercentFactor(entries[i])
                        a += b
                        b = 0.0
                    }
                    DIVISION.tag -> {
                        b /= getDivisor(entries, i)
                        a += b
                        b = 0.0
                    }
                    else -> throw IllegalStateException("Error checking minus tag")
                }
                MULTIPLY.tag -> when (entries[i - 1]) {
                    PLUS.tag -> b = getEntryWithPercentFactor(entries[i])
                    MINUS.tag -> b = -getEntryWithPercentFactor(entries[i])
                    MULTIPLY.tag -> b *= getEntryWithPercentFactor(entries[i])
                    DIVISION.tag -> b /= getDivisor(entries, i)
                    else -> throw IllegalStateException("Error checking multiply tag")
                }
                DIVISION.tag -> when (entries[i - 1]) {
                    PLUS.tag -> b = getEntryWithPercentFactor(entries[i])
                    MINUS.tag -> b = -getEntryWithPercentFactor(entries[i])
                    MULTIPLY.tag -> b *= getEntryWithPercentFactor(entries[i])
                    DIVISION.tag -> b /= getDivisor(entries, i)
                    else -> throw IllegalStateException("Error checking division tag")
                }
                else -> throw IllegalStateException("Error checking next operator tag")
            }
        }
        throw IllegalStateException("Error solving equation")
    }

    private fun getDivisor(entries: List<String>, i: Int): Double {
        val divisor = getEntryWithPercentFactor(entries[i])
        if (divisor == 0.0) throw DivideByZeroException()
        return divisor
    }

    private fun getEntryWithPercentFactor(entry: String, baseNumber: Double): Double {
        return when {
            entry == DECIMAL.tag -> 0.0
            entry.endsWith(PERCENT.tag) -> baseNumber * entry.trimEndChar().toDouble() / 100.0
            else -> entry.toDouble()
        }
    }

    private fun getEntryWithPercentFactor(entry: String): Double {
        return when {
            entry == DECIMAL.tag -> 1.0
            entry.endsWith(PERCENT.tag) -> entry.trimEndChar().toDouble() / 100.0
            else -> entry.toDouble()
        }
    }
}
