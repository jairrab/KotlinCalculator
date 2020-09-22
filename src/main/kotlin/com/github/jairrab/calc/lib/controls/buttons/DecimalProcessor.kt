/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.buttons

import com.github.jairrab.calc.CalculatorButton.DECIMAL
import com.github.jairrab.calc.lib.controls.entries.EntriesManager


class DecimalProcessor(
    private val entriesManager: EntriesManager
) {
    fun processDecimal() {
        if (entriesManager.isNoEntries()) {
            entriesManager.addEntry(DECIMAL.tag)
        } else {
            when {
                entriesManager.isLastEntryAnOperator() -> entriesManager.addEntry(DECIMAL.tag)
                entriesManager.isLastEntryANumberWithDecimal() -> return
                entriesManager.isLastEntryANumber() -> {
                    entriesManager.setLastEntry(entriesManager.getLastEntry() + DECIMAL.tag)
                }
                entriesManager.isLastEntryADecimal() -> return
                else -> throw IllegalStateException("Invalid decimal command")
            }
        }
    }
}