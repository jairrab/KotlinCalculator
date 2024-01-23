/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.mathutils

import com.github.jairrab.calc.CalculatorType
import com.github.jairrab.calc.lib.mathutils.calculators.BasicMdasCalculator
import com.github.jairrab.calc.lib.mathutils.calculators.BasicNonMdasCalculator
import java.math.BigDecimal

class EquationSolver private constructor(
    private val entriesCalculator: EntriesCalculator,
) {
    fun solve(entries: List<String>): BigDecimal {
        return entriesCalculator.solve(entries)
    }

    companion object {
        fun getInstance(calculatorType: CalculatorType): EquationSolver {
            val entriesCalculator = when (calculatorType) {
                CalculatorType.BASIC_MDAS -> BasicMdasCalculator()
                CalculatorType.BASIC_NON_MDAS -> BasicNonMdasCalculator()
            }
            return EquationSolver(entriesCalculator)
        }
    }
}
