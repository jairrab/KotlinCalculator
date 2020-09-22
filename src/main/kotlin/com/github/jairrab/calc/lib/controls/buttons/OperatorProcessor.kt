/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.buttons

import com.github.jairrab.calc.CalculatorButton
import com.github.jairrab.calc.CalculatorButton.ZERO
import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import com.github.jairrab.calc.lib.utils.trimEndChar

class OperatorProcessor(
    private val entriesManager: EntriesManager
) {
    internal fun processOperator(calculatorButton: CalculatorButton) {
        val operator = calculatorButton.tag

        if (entriesManager.isNoEntries()) {
            entriesManager.addEntry(ZERO.tag)
            entriesManager.addEntry(operator)
        } else {
            if (entriesManager.isLastEntryAnOperator()) {
                entriesManager.removeLastEntry()
                entriesManager.addEntry(operator)
            } else if (entriesManager.isLastEntryANumber()) {
                if (entriesManager.isLastEntryEndsWithDecimal()) {
                    entriesManager.setLastEntry(entriesManager.getLastEntry().trimEndChar())
                    entriesManager.addEntry(operator)
                } else {
                    entriesManager.addEntry(operator)
                }
            } else if (entriesManager.isLastEntryADecimal()) {
                if (entriesManager.isSingleEntry()) {
                    entriesManager.removeLastEntry()
                    entriesManager.addEntry(ZERO.tag)
                    entriesManager.addEntry(operator)
                } else {
                    entriesManager.removeLastEntry()
                    entriesManager.removeLastEntry()
                    entriesManager.addEntry(operator)
                }
            } else {
                entriesManager.addEntry(operator)
            }
        }
    }
}