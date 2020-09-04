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
                    entriesManager.setLastEntry(entriesManager.getLastEntry().trimEndChar())
                }
                entriesManager.isLastEntryAnOperator() -> entriesManager.removeLastEntry()
                entriesManager.isLastEntryADecimal() -> entriesManager.removeLastEntry()
            }
        }

        displayManager.update("backspace")
    }
}