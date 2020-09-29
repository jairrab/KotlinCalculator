/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls

import com.github.jairrab.calc.Calculator
import com.github.jairrab.calc.CalculatorType
import com.github.jairrab.calc.lib.controls.buttons.*
import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import com.github.jairrab.calc.lib.controls.outputs.OutputManager

internal class ControlProcessor private constructor(
    val outputManager: OutputManager,
    val clearProcessor: ClearProcessor,
    val backspaceProcessor: BackspaceProcessor,
    val decimalProcessor: DecimalProcessor,
    val numberProcessor: NumberProcessor,
    val operatorProcessor: OperatorProcessor,
    val percentProcessor: PercentProcessor,
) {
    fun setListener(listener: Calculator.Listener) {
        outputManager.listener = listener
    }

    companion object {
        fun getInstance(
            entriesManager: EntriesManager,
            calculatorType: CalculatorType,
            listener: Calculator.Listener?
        ): ControlProcessor {
            val displayManager = OutputManager
                .getInstance(entriesManager, calculatorType, listener)

            return ControlProcessor(
                outputManager = displayManager,
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