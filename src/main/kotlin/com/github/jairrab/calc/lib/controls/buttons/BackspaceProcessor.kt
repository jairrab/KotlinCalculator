@file:Suppress("UseCheckOrError")

/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.buttons

import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import com.github.jairrab.calc.lib.utils.trimEndChar

internal class BackspaceProcessor(
    private val entriesManager: EntriesManager
) {
    fun onBackSpace() {
        if (entriesManager.hasEntries()) {
            when {
                entriesManager.isReadyToClear() -> {
                    entriesManager.setReadyToClear(false)
                    val entry = entriesManager.getLastEntry().trimEndChar()
                    if (entry.isNotEmpty()) {
                        entriesManager.setLastEntry(entry)
                    } else {
                        entriesManager.removeLastEntry()
                    }
                }
                entriesManager.isLastEntryAPercentNumber() -> {
                    val entry = entriesManager.getLastEntry().trimEndChar()
                    entriesManager.setLastEntry(entry)
                }
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
    }
}
