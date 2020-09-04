/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls

import com.github.jairrab.calc.lib.utils.trimEndChar

class BackspaceProcessor(
    private val entriesManager: EntriesManager,
    private val displayManager: DisplayManager
) {
    fun onBackSpace() {
        if (entriesManager.hasEntries()) {
            when {
                entriesManager.isLastEntryANumber() -> {
                    val entry = entriesManager.getLastEntry().trimEndChar()
                    if (entry.isNotEmpty()) {
                        entriesManager.setLastEntry(entry)
                    } else {
                        entriesManager.removeLastEntry()
                    }
                }
                entriesManager.isLastEntryAnOperator() -> entriesManager.removeLastEntry()
                entriesManager.isLastEntryADecimal() -> entriesManager.removeLastEntry()
                else -> throw IllegalStateException("Invalid backspace command")
            }
        }

        displayManager.update("backspace")
    }
}