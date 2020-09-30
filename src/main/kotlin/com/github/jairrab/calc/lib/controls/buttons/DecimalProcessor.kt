/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.buttons

import com.github.jairrab.calc.CalculatorButton.DECIMAL
import com.github.jairrab.calc.CalculatorUpdate.Error.InvalidKey
import com.github.jairrab.calc.InvalidKeyType.INVALID_DECIMAL_ENTRY
import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import com.github.jairrab.calc.lib.controls.outputs.OutputManager
import com.github.jairrab.calc.lib.utils.trimEndChar


internal class DecimalProcessor(
    private val entriesManager: EntriesManager,
    private val outputManager: OutputManager
) {
    fun processDecimal() {
        if (entriesManager.isNoEntries()) {
            entriesManager.addEntry(DECIMAL.tag)
        } else {
            when {
                entriesManager.isReadyToClear() -> {
                    entriesManager.setReadyToClear(false)
                    entriesManager.clearEntries()
                    entriesManager.addEntry(DECIMAL.tag)
                }
                entriesManager.isLastEntryAnOperator() -> entriesManager.addEntry(DECIMAL.tag)
                entriesManager.isLastEntryANumberWithDecimal() -> {
                    val entries = entriesManager.getEntries()
                    outputManager.updateListener(InvalidKey(INVALID_DECIMAL_ENTRY, entries))
                    return
                }
                entriesManager.isLastEntryAPercentNumber() -> {
                    val entry = entriesManager.getLastEntry().trimEndChar()
                    entriesManager.setLastEntry(entry)
                    entriesManager.appendToLastEntry(DECIMAL.tag)
                }
                entriesManager.isLastEntryANumber() -> {
                    entriesManager.appendToLastEntry(DECIMAL.tag)
                }
                entriesManager.isLastEntryADecimal() -> return
                else -> throw IllegalStateException("Invalid decimal command")
            }
        }
    }
}