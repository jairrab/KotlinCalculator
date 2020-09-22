/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.mathutils.calculators

import com.github.jairrab.calc.CalculatorButton.*
import com.github.jairrab.calc.lib.mathutils.EntriesCalculator
import com.github.jairrab.calc.lib.mathutils.OperatorUtils.getOperators
import com.github.jairrab.calc.lib.utils.splitList

class BasicMdasCalculator : EntriesCalculator {
    override fun solve(entries: List<String>): Double {
        var result = 0.0

        result = doPlus(entries, result)

        return result
    }

    private fun doPlus(entries: List<String>, initialNumber: Double): Double {
        var result = initialNumber

        val splits = entries.splitList(PLUS.tag)

        for (split in splits) {
            if (getOperators(split).contains(MINUS.tag)) {
                result = doMinus(split, result)
            } else {
                result += doMultiply(split)
            }
        }

        return result
    }

    private fun doMinus(entries: List<String>, initialNumber: Double): Double {
        var result = initialNumber

        val splits = entries.splitList(MINUS.tag)

        for (index in splits.indices) {
            val multiplyResult = doMultiply(splits[index])

            if (index == 0) {
                result += multiplyResult
            } else {
                result -= multiplyResult
            }
        }

        return result
    }

    private fun doMultiply(entries: List<String>): Double {
        var result = 1.0

        val splits = entries.splitList(MULTIPLY.tag)

        for (split in splits) {
            if (getOperators(split).contains(DIVISION.tag)) {
                result = doDivide(split, result)

            } else {
                val number = if (split[0] == DECIMAL.tag) {
                    0.0
                } else {
                    split[0].toDouble()
                }

                result *= number
            }
        }

        return result
    }

    private fun doDivide(entries: List<String>, initialNumber: Double): Double {
        var result = initialNumber

        val splits = entries.splitList(DIVISION.tag)

        for (index in splits.indices) {
            //at this point, all remaining are numbers
            val number = if (splits[index][0] == DECIMAL.tag) {
                1.0
            } else {
                splits[index][0].toDouble()
            }

            result *= if (index == 0) {
                number
            } else {
                1 / number
            }
        }

        return result
    }
}