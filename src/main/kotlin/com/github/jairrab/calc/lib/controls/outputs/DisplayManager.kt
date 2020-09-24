/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.outputs

import com.github.jairrab.calc.Calculator
import com.github.jairrab.calc.CalculatorButton
import com.github.jairrab.calc.CalculatorType
import com.github.jairrab.calc.CalculatorUpdate
import com.github.jairrab.calc.CalculatorUpdate.Error
import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import com.github.jairrab.calc.lib.mathutils.DivideByZeroException
import com.github.jairrab.calc.lib.mathutils.EquationSolver
import com.github.jairrab.calc.lib.utils.Logger.LOG

class DisplayManager private constructor(
    var listener: Calculator.Listener?,
    private val entriesManager: EntriesManager,
    private val equationSolver: EquationSolver
) {
    fun update(button: CalculatorButton) {
        val entries = entriesManager.getEntries()
        try {
            val result = when {
                entriesManager.isNoEntries() -> 0.0
                entriesManager.isSingleEntry() -> when {
                    entriesManager.isLastEntryADecimal() -> 0.0
                    entriesManager.isLastEntryAPercentNumber() -> entriesManager.getLastDoubleEntry()
                    entriesManager.isLastEntryANumber() -> entriesManager.getLastDoubleEntry()
                    else -> {
                        throw IllegalStateException("Invalid entry: ${entriesManager.getLastEntry()}")
                    }
                }
                else -> equationSolver.solve(entries)
            }

            LOG.info("Key: ${button.tag} | Entries: $entries | Result: $result")
            updateListener(CalculatorUpdate.OnUpdate(button.tag, entries, result))

            if (button == CalculatorButton.EQUALS) {
                entriesManager.lastResult = result
            }
        } catch (e: DivideByZeroException) {
            LOG.warning("Divide by zero error")
            listener?.onCalculatorUpdate(Error.DivideByZero(button.tag, entries))
        }
    }

    fun update(number: Double) {
        updateListener(CalculatorUpdate.Initializing(number))
        LOG.info("Initializing calculator")
    }

    fun updateListener(calculatorUpdate: CalculatorUpdate) {
        listener?.onCalculatorUpdate(calculatorUpdate)
    }

    companion object {
        fun getInstance(
            entriesManager: EntriesManager,
            calculatorType: CalculatorType,
            listener: Calculator.Listener?
        ): DisplayManager {
            val equationSolver = EquationSolver.getInstance(calculatorType)
            return DisplayManager(listener, entriesManager, equationSolver)
        }
    }
}