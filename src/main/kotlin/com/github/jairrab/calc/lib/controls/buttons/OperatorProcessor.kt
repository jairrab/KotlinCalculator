/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.buttons

import com.github.jairrab.calc.Calculator
import com.github.jairrab.calc.CalculatorButton
import com.github.jairrab.calc.CalculatorButton.ZERO
import com.github.jairrab.calc.CalculatorUpdate.InvalidKey
import com.github.jairrab.calc.InvalidKeyType.INVALID_OPERATOR_ENTRY
import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import com.github.jairrab.calc.lib.controls.outputs.DisplayManager
import com.github.jairrab.calc.lib.utils.trimEndChar

class OperatorProcessor(
    private val entriesManager: EntriesManager,
    private val displayManager: DisplayManager
) {
    internal fun processOperator(calculatorButton: CalculatorButton) {
        val operator = calculatorButton.tag

        if (entriesManager.isNoEntries()) {
            entriesManager.addEntry(ZERO.tag)
            entriesManager.addEntry(operator)
        } else {
            when {
                entriesManager.lastResult != null  -> {
                    entriesManager.setToLastResult()
                    entriesManager.lastResult = null
                    entriesManager.addEntry(operator)
                }
                entriesManager.isLastEntryAnOperator() -> {
                    entriesManager.removeLastEntry()
                    entriesManager.addEntry(operator)
                }
                entriesManager.isLastEntryAPercentNumber() -> {
                    entriesManager.addEntry(operator)
                }
                entriesManager.isLastEntryANumber() -> {
                    if (entriesManager.isLastEntryEndsWithDecimal()) {
                        entriesManager.setLastEntry(entriesManager.getLastEntry().trimEndChar())
                        entriesManager.addEntry(operator)
                    } else {
                        entriesManager.addEntry(operator)
                    }
                }
                entriesManager.isLastEntryADecimal() -> {
                    if (entriesManager.isSingleEntry()) {
                        entriesManager.removeLastEntry()
                        entriesManager.addEntry(ZERO.tag)
                        entriesManager.addEntry(operator)
                    } else {
                        entriesManager.removeLastEntry()
                        entriesManager.removeLastEntry()
                        entriesManager.addEntry(operator)
                    }
                }
                else -> {
                    displayManager.updateListener(InvalidKey(INVALID_OPERATOR_ENTRY))
                    throw IllegalStateException("Invalid operator entry")
                }
            }
        }
    }
}