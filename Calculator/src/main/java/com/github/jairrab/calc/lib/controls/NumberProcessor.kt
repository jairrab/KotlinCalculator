/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls

class NumberProcessor(
    private val entriesManager: EntriesManager,
    private val displayManager: DisplayManager
) {
    fun processNumber(number: Int) {
        if (entriesManager.isNoEntries()) {
            entriesManager.addEntry(number.toString())
        } else {
            when {
                entriesManager.isLastEntryAnOperator() -> {
                    entriesManager.addEntry(number.toString())
                }
                entriesManager.isLastEntryANumber() -> {
                    if (entriesManager.getLastEntry() == "0") {
                        entriesManager.setLastEntry(number.toString())
                    } else {
                        entriesManager.setLastEntry(entriesManager.getLastEntry() + number)
                    }
                }
                entriesManager.isLastEntryADecimal() -> {
                    entriesManager.setLastEntry(entriesManager.getLastEntry() + number)
                }
                else -> throw IllegalStateException("Invalid number command")
            }
        }

        displayManager.update(number.toString())
    }
}