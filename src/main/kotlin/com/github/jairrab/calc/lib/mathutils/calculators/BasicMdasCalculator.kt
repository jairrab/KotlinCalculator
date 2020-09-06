/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.mathutils.calculators

import com.github.jairrab.calc.lib.mathutils.DECIMAL
import com.github.jairrab.calc.lib.mathutils.EntriesCalculator
import com.github.jairrab.calc.lib.mathutils.Operator.*
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

        val splits = entries.splitList(Plus.symbol)

        for (split in splits) {
            if (getOperators(split).contains(Minus.symbol)) {
                result = doMinus(split, result)
            } else {
                result += doMultiply(split)
            }
        }

        return result
    }

    private fun doMinus(entries: List<String>, initialNumber: Double): Double {
        var result = initialNumber

        val splits = entries.splitList(Minus.symbol)

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

        val splits = entries.splitList(Multiply.symbol)

        for (split in splits) {
            if (getOperators(split).contains(Divide.symbol)) {
                result = doDivide(split, result)

            } else {
                val number = if (split[0] == DECIMAL) {
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

        val splits = entries.splitList(Divide.symbol)

        for (index in splits.indices) {
            //at this point, all remaining are numbers
            val number = if (splits[index][0] == DECIMAL) {
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