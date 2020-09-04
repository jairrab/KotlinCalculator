/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc

import com.github.jairrab.calc.lib.CalculatorUtility
import com.github.jairrab.calc.lib.controls.*
import com.github.jairrab.calc.lib.mathutils.EquationSolver


interface Calculator {
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
        fun getInstance(listener: Listener) = getInstance(0.0, listener)

        @JvmStatic
        fun getInstance(initialNumber: Double = 0.0, listener: Listener): Calculator {
            val entriesManager = EntriesManager()
            val equationSolver = EquationSolver()
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
