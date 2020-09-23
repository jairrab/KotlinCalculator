/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.buttons

import com.github.jairrab.calc.CalculatorButton
import com.github.jairrab.calc.CalculatorUpdate.InvalidKey
import com.github.jairrab.calc.InvalidKeyType.INVALID_PERCENT_ENTRY
import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import com.github.jairrab.calc.lib.controls.outputs.DisplayManager
import com.github.jairrab.calc.lib.utils.trimEndChar

class PercentProcessor(
    private val entriesManager: EntriesManager,
    private val displayManager: DisplayManager
) {
    internal fun processPercent() {
        if (entriesManager.isNoEntries()) {
            displayManager.updateListener(InvalidKey(INVALID_PERCENT_ENTRY))
            return
        } else {
            when {
                entriesManager.lastResult != null -> {
                    displayManager.updateListener(InvalidKey(INVALID_PERCENT_ENTRY))
                    return
                }
                entriesManager.isLastEntryAnOperator() -> {
                    displayManager.updateListener(InvalidKey(INVALID_PERCENT_ENTRY))
                    return
                }
                entriesManager.isLastEntryAPercentNumber() -> {
                    displayManager.updateListener(InvalidKey(INVALID_PERCENT_ENTRY))
                    return
                }
                entriesManager.isLastEntryANumber() -> {
                    if (entriesManager.isLastEntryEndsWithDecimal()) {
                        entriesManager.setLastEntry(entriesManager.getLastEntry().trimEndChar())
                    }
                    entriesManager.appendToLastEntry(CalculatorButton.PERCENT.tag)
                }
                entriesManager.isLastEntryADecimal() -> {
                    displayManager.updateListener(InvalidKey(INVALID_PERCENT_ENTRY))
                    return
                }
                else -> {
                    displayManager.updateListener(InvalidKey(INVALID_PERCENT_ENTRY))
                    throw IllegalStateException("Invalid operator entry")
                }
            }
        }
    }
}