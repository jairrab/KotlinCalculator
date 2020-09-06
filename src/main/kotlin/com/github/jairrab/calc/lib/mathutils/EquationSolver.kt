/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.mathutils

import com.github.jairrab.calc.CalculatorType
import com.github.jairrab.calc.lib.mathutils.calculators.BasicMdasCalculator
import com.github.jairrab.calc.lib.mathutils.calculators.BasicNonMdasCalculator

class EquationSolver(
    private val calculatorType: CalculatorType,
    private val basicMdasCalculator: BasicMdasCalculator,
    private val basicNonMdasCalculator: BasicNonMdasCalculator
) {
    fun solve(entries: List<String>): Double {
        return when (calculatorType) {
            CalculatorType.BASIC_MDAS -> basicMdasCalculator.solve(entries)
            CalculatorType.BASIC_NON_MDAS -> basicNonMdasCalculator.solve(entries)
        }
    }
}