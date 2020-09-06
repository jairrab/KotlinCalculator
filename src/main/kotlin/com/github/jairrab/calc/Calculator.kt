/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc

import com.github.jairrab.calc.lib.CalculatorUtility
import com.github.jairrab.calc.lib.controls.*
import com.github.jairrab.calc.lib.mathutils.EquationSolver
import com.github.jairrab.calc.lib.mathutils.calculators.BasicMdasCalculator
import com.github.jairrab.calc.lib.mathutils.calculators.BasicNonMdasCalculator


interface Calculator {
    fun press(button: CalculatorButton)
    fun clear()
    fun pressOne()
    fun pressTwo()
    fun pressThree()
    fun pressFour()
    fun pressFive()
    fun pressSix()
    fun pressSeven()
    fun pressEight()
    fun pressNine()
    fun pressZero()
    fun pressDecimal()
    fun pressPlus()
    fun pressMinus()
    fun pressMultiply()
    fun pressDivide()
    fun backSpace()
    fun pressEquals()

    interface Listener {
        fun onCalculatorUpdate(key: String?, entries: List<String>, result: Double)
    }

    companion object {
        @JvmStatic
        fun getInstance(listener: Listener) = getInstance(
            calculatorType = CalculatorType.BASIC_MDAS,
            initialNumber = 0.0,
            listener = listener
        )

        @JvmStatic
        fun getInstance(
            calculatorType: CalculatorType = CalculatorType.BASIC_MDAS,
            initialNumber: Double = 0.0,
            listener: Listener
        ): Calculator {
            val entriesManager = EntriesManager()
            val equationSolver = EquationSolver(
                calculatorType = calculatorType,
                basicMdasCalculator = BasicMdasCalculator(),
                basicNonMdasCalculator = BasicNonMdasCalculator()
            )
            val displayManager = DisplayManager(listener, entriesManager, equationSolver)

            return CalculatorUtility(
                initialNumber = initialNumber,
                clearProcessor = ClearProcessor(entriesManager, displayManager),
                backspaceProcessor = BackspaceProcessor(entriesManager, displayManager),
                decimalProcessor = DecimalProcessor(entriesManager, displayManager),
                numberProcessor = NumberProcessor(entriesManager, displayManager),
                operationProcessor = OperationProcessor(entriesManager, displayManager)
            )
        }
    }
}
