/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls

import com.github.jairrab.calc.Calculator
import com.github.jairrab.calc.CalculatorType
import com.github.jairrab.calc.lib.controls.buttons.*
import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import com.github.jairrab.calc.lib.controls.outputs.DisplayManager

class ControlProcessor private constructor(
    val displayManager: DisplayManager,
    val clearProcessor: ClearProcessor,
    val backspaceProcessor: BackspaceProcessor,
    val decimalProcessor: DecimalProcessor,
    val numberProcessor: NumberProcessor,
    val operatorProcessor: OperatorProcessor,
    val percentProcessor: PercentProcessor,
) {
    fun setListener(listener: Calculator.Listener) {
        displayManager.listener = listener
    }

    companion object {
        fun getInstance(
            entriesManager: EntriesManager,
            calculatorType: CalculatorType,
            listener: Calculator.Listener?
        ): ControlProcessor {
            val displayManager = DisplayManager
                .getInstance(entriesManager, calculatorType, listener)

            return ControlProcessor(
                displayManager = displayManager,
                clearProcessor = ClearProcessor(entriesManager),
                backspaceProcessor = BackspaceProcessor(entriesManager),
                decimalProcessor = DecimalProcessor(entriesManager, displayManager),
                numberProcessor = NumberProcessor(entriesManager),
                operatorProcessor = OperatorProcessor(entriesManager, displayManager),
                percentProcessor = PercentProcessor(entriesManager, displayManager)
            )
        }
    }
}