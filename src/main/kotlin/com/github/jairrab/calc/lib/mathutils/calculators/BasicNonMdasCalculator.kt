/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.mathutils.calculators

import com.github.jairrab.calc.CalculatorButton.*
import com.github.jairrab.calc.lib.mathutils.DivideByZeroException
import com.github.jairrab.calc.lib.mathutils.EntriesCalculator
import com.github.jairrab.calc.lib.mathutils.OperatorUtils.isOperator
import com.github.jairrab.calc.lib.utils.trimEndChar

class BasicNonMdasCalculator : EntriesCalculator {
    override fun solve(entries: List<String>): Double {
        val entriesToProcess = entries.toMutableList().apply {
            if (isOperator(last())) removeLast()
        }

        return calculate(entriesToProcess)
    }

    private fun calculate(entries: List<String>): Double {
        var a = 0.0

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
                when (entries[i - 1]) {
                    PLUS.tag -> a += getEntryWithPercentFactor(entries[i], a)
                    MINUS.tag -> a -= getEntryWithPercentFactor(entries[i], a)
                    MULTIPLY.tag -> a *= getEntryWithPercentFactor(entries[i])
                    DIVISION.tag -> a /= getDivisor(entries, i)
                    else -> throw IllegalStateException("Error solving last entry")
                }
                return a
            }

            when (entries[i + 1]) {
                PLUS.tag -> when (entries[i - 1]) {
                    PLUS.tag -> a += getEntryWithPercentFactor(entries[i], a)
                    MINUS.tag -> a -= getEntryWithPercentFactor(entries[i], a)
                    MULTIPLY.tag -> a *= getEntryWithPercentFactor(entries[i])
                    DIVISION.tag -> a /= getDivisor(entries, i)
                    else -> throw IllegalStateException("Error checking plus tag")
                }
                MINUS.tag -> when (entries[i - 1]) {
                    PLUS.tag -> a += getEntryWithPercentFactor(entries[i], a)
                    MINUS.tag -> a -= getEntryWithPercentFactor(entries[i], a)
                    MULTIPLY.tag -> a *= getEntryWithPercentFactor(entries[i])
                    DIVISION.tag -> a /= getDivisor(entries, i)
                    else -> throw IllegalStateException("Error checking minus tag")
                }
                MULTIPLY.tag -> when (entries[i - 1]) {
                    PLUS.tag -> a += getEntryWithPercentFactor(entries[i])
                    MINUS.tag -> a -= getEntryWithPercentFactor(entries[i])
                    MULTIPLY.tag -> a *= getEntryWithPercentFactor(entries[i])
                    DIVISION.tag -> a /= getDivisor(entries, i)
                    else -> throw IllegalStateException("Error checking multiply tag")
                }
                DIVISION.tag -> when (entries[i - 1]) {
                    PLUS.tag -> a += getEntryWithPercentFactor(entries[i])
                    MINUS.tag -> a -= getEntryWithPercentFactor(entries[i])
                    MULTIPLY.tag -> a *= getEntryWithPercentFactor(entries[i])
                    DIVISION.tag -> a /= getDivisor(entries, i)
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
        return if (entry.endsWith(PERCENT.tag)) {
            baseNumber * entry.trimEndChar().toDouble() / 100.0
        } else {
            entry.toDouble()
        }
    }

    private fun getEntryWithPercentFactor(entry:String): Double {
        return if (entry.endsWith(PERCENT.tag)) {
            entry.trimEndChar().toDouble() / 100.0
        } else {
            entry.toDouble()
        }
    }
}