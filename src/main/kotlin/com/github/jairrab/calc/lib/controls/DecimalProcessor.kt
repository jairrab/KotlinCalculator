/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls

import com.github.jairrab.calc.lib.mathutils.DECIMAL


class DecimalProcessor(
    private val entriesManager: EntriesManager,
    private val displayManager: DisplayManager
) {
    fun processDecimal() {
        if (entriesManager.isNoEntries()) {
            entriesManager.addEntry(DECIMAL)
        } else {
            when {
                entriesManager.isLastEntryAnOperator() -> entriesManager.addEntry(DECIMAL)
                entriesManager.isLastEntryANumberWithDecimal() -> return
                entriesManager.isLastEntryANumber() -> {
                    entriesManager.setLastEntry(entriesManager.getLastEntry() + DECIMAL)
                }
                entriesManager.isLastEntryADecimal() -> return
                else -> throw IllegalStateException("Invalid decimal command")
            }
        }

        displayManager.update(DECIMAL)
    }
}