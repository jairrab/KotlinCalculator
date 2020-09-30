/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.buttons

import com.github.jairrab.calc.CalculatorButton
import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import com.github.jairrab.calc.lib.utils.trimEndChar

internal class NumberProcessor(
    private val entriesManager: EntriesManager
) {
    fun processNumber(calculatorButton: CalculatorButton) {
        val number = calculatorButton.tag
        when {
            entriesManager.isNoEntries() -> {
                entriesManager.setReadyToClear(false)
                entriesManager.addEntry(number)
            }
            entriesManager.isReadyToClear() -> {
                entriesManager.setReadyToClear(false)
                entriesManager.clearEntries()
                entriesManager.addEntry(number)
            }
            entriesManager.isLastEntryAnOperator() -> {
                entriesManager.addEntry(number)
            }
            entriesManager.isLastEntryAPercentNumber() -> {
                val entry = entriesManager.getLastEntry().trimEndChar()
                entriesManager.setLastEntry(entry)
                entriesManager.appendToLastEntry(number)
            }
            entriesManager.isLastEntryANumber() -> {
                if (entriesManager.getLastEntry() == "0") {
                    entriesManager.setLastEntry(number)
                } else {
                    entriesManager.appendToLastEntry(number)
                }
            }
            entriesManager.isLastEntryADecimal() -> {
                entriesManager.appendToLastEntry(number)
            }
            else -> throw IllegalStateException("Invalid number command")
        }
    }
}