/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls

import com.github.jairrab.calc.Calculator
import com.github.jairrab.calc.CalculatorType
import com.github.jairrab.calc.lib.controls.buttons.BackspaceProcessor
import com.github.jairrab.calc.lib.controls.buttons.ClearProcessor
import com.github.jairrab.calc.lib.controls.buttons.DecimalProcessor
import com.github.jairrab.calc.lib.controls.buttons.NumberProcessor
import com.github.jairrab.calc.lib.controls.buttons.OperatorProcessor
import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import com.github.jairrab.calc.lib.controls.outputs.DisplayManager

class ControlProcessor private constructor(
    val displayManager: DisplayManager,
    val clearProcessor: ClearProcessor,
    val backspaceProcessor: BackspaceProcessor,
    val decimalProcessor: DecimalProcessor,
    val numberProcessor: NumberProcessor,
    val operatorProcessor: OperatorProcessor
) {
    companion object {
        fun getInstance(
            entriesManager: EntriesManager,
            calculatorType: CalculatorType,
            listener: Calculator.Listener
        ) = ControlProcessor(
            displayManager = DisplayManager.getInstance(entriesManager, calculatorType, listener),
            clearProcessor = ClearProcessor(entriesManager),
            backspaceProcessor = BackspaceProcessor(entriesManager),
            decimalProcessor = DecimalProcessor(entriesManager),
            numberProcessor = NumberProcessor(entriesManager),
            operatorProcessor = OperatorProcessor(entriesManager)
        )
    }
}