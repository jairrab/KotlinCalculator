/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.buttons

import com.github.jairrab.calc.CalculatorButton
import com.github.jairrab.calc.CalculatorUpdate.Error.InvalidKey
import com.github.jairrab.calc.InvalidKeyType.INVALID_PERCENT_ENTRY
import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import com.github.jairrab.calc.lib.controls.outputs.OutputManager
import com.github.jairrab.calc.lib.utils.trimEndChar

internal class PercentProcessor(
    private val entriesManager: EntriesManager,
    private val outputManager: OutputManager
) {
    internal fun processPercent() {
        val entries = entriesManager.getEntries()
        if (entriesManager.isNoEntries()) {
            outputManager.updateListener(InvalidKey(INVALID_PERCENT_ENTRY, entries))
            return
        } else {
            when {
                entriesManager.lastResult != null -> {
                    outputManager.updateListener(InvalidKey(INVALID_PERCENT_ENTRY, entries))
                    return
                }
                entriesManager.isLastEntryAnOperator() -> {
                    outputManager.updateListener(InvalidKey(INVALID_PERCENT_ENTRY, entries))
                    return
                }
                entriesManager.isLastEntryAPercentNumber() -> {
                    outputManager.updateListener(InvalidKey(INVALID_PERCENT_ENTRY, entries))
                    return
                }
                entriesManager.isLastEntryANumber() -> {
                    if (entriesManager.isLastEntryEndsWithDecimal()) {
                        entriesManager.setLastEntry(entriesManager.getLastEntry().trimEndChar())
                    }
                    entriesManager.appendToLastEntry(CalculatorButton.PERCENT.tag)
                }
                entriesManager.isLastEntryADecimal() -> {
                    outputManager.updateListener(InvalidKey(INVALID_PERCENT_ENTRY, entries))
                    return
                }
                else -> {
                    outputManager.updateListener(InvalidKey(INVALID_PERCENT_ENTRY, entries))
                    throw IllegalStateException("Invalid operator entry")
                }
            }
        }
    }
}