/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.buttons

import com.github.jairrab.calc.CalculatorButton
import com.github.jairrab.calc.CalculatorButton.ZERO
import com.github.jairrab.calc.CalculatorUpdate.Error.InvalidKey
import com.github.jairrab.calc.InvalidKeyType.INVALID_OPERATOR_ENTRY
import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import com.github.jairrab.calc.lib.controls.outputs.OutputManager
import com.github.jairrab.calc.lib.utils.trimEndChar

internal class OperatorProcessor(
    private val entriesManager: EntriesManager,
    private val outputManager: OutputManager
) {
    internal fun processOperator(calculatorButton: CalculatorButton) {
        val operator = calculatorButton.tag

        if (entriesManager.isNoEntries()) {
            entriesManager.addEntry(ZERO.tag)
            entriesManager.addEntry(operator)
        } else {
            when {
                entriesManager.isReadyToClear()  -> {
                    entriesManager.setEntriesToResult()
                    entriesManager.setReadyToClear(false)
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
                    val entries = entriesManager.getEntries()
                    outputManager.updateListener(InvalidKey(INVALID_OPERATOR_ENTRY, entries))
                    throw IllegalStateException("Invalid operator entry")
                }
            }
        }
    }
}