/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.buttons

import com.github.jairrab.calc.CalculatorButton
import com.github.jairrab.calc.lib.controls.entries.EntriesManager

class NumberProcessor(
    private val entriesManager: EntriesManager
) {
    fun processNumber(calculatorButton: CalculatorButton) {
        val number = calculatorButton.tag
        if (entriesManager.isNoEntries()) {
            entriesManager.addEntry(number)
        } else {
            when {
                entriesManager.isLastEntryAnOperator() -> {
                    entriesManager.addEntry(number)
                }
                entriesManager.isLastEntryANumber() -> {
                    if (entriesManager.getLastEntry() == "0") {
                        entriesManager.setLastEntry(number)
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
    }
}