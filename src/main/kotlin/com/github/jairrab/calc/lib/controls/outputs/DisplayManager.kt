/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.outputs

import com.github.jairrab.calc.Calculator
import com.github.jairrab.calc.CalculatorType
import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import com.github.jairrab.calc.lib.mathutils.EquationSolver
import com.github.jairrab.calc.lib.utils.Logger.LOG

class DisplayManager private constructor(
    private val listener: Calculator.Listener,
    private val entriesManager: EntriesManager,
    private val equationSolver: EquationSolver
) {
    fun update(key: String) {
        val result = when {
            entriesManager.isNoEntries() -> 0.0
            entriesManager.isSingleEntry() -> when {
                entriesManager.isLastEntryADecimal() -> 0.0
                entriesManager.isLastEntryANumber() -> entriesManager.getLastEntry().toDouble()
                else -> throw IllegalStateException("Invalid entry")
            }
            else -> equationSolver.solve(entriesManager.getEntries())
        }

        LOG.info("Key: $key | Entries: ${entriesManager.getEntries()} | Result: $result")
        listener.onCalculatorUpdate(key, entriesManager.getEntries(), result)
    }

    fun resetEntries() {
        entriesManager.clear()
    }

    companion object {
        fun getInstance(
            entriesManager: EntriesManager,
            calculatorType: CalculatorType,
            listener: Calculator.Listener
        ): DisplayManager {
            val equationSolver = EquationSolver.getInstance(calculatorType)
            return DisplayManager(listener, entriesManager, equationSolver)
        }
    }
}