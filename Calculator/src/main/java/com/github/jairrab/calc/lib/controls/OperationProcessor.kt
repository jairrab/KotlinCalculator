/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls

import com.github.jairrab.calc.lib.mathutils.Operator
import com.github.jairrab.calc.lib.mathutils.OperatorUtils
import com.github.jairrab.calc.lib.utils.trimEndChar

class OperationProcessor(
    private val entriesManager: EntriesManager,
    private val displayManager: DisplayManager
) {
    internal fun processEquals() {
        processOperator(Operator.Equals)
        entriesManager.clear()
    }

    internal fun processOperator(operator: Operator) {
        if (entriesManager.isNoEntries()) {
            entriesManager.addEntry("0")
            entriesManager.addEntry(OperatorUtils.getOperatorSymbol(operator))
        } else {
            if (entriesManager.isLastEntryAnOperator()) {
                entriesManager.removeLastEntry()
                entriesManager.addEntry(OperatorUtils.getOperatorSymbol(operator))
            } else if (entriesManager.isLastEntryANumber()) {
                if (entriesManager.isLastEntryEndsWithDecimal()) {
                    entriesManager.setLastEntry(entriesManager.getLastEntry().trimEndChar())
                    entriesManager.addEntry(OperatorUtils.getOperatorSymbol(operator))
                } else {
                    entriesManager.addEntry(OperatorUtils.getOperatorSymbol(operator))
                }
            } else if (entriesManager.isLastEntryADecimal()) {
                if (entriesManager.isSingleEntry()) {
                    entriesManager.removeLastEntry()
                    entriesManager.addEntry("0")
                    entriesManager.addEntry(OperatorUtils.getOperatorSymbol(operator))
                } else {
                    entriesManager.removeLastEntry()
                    entriesManager.removeLastEntry()
                    entriesManager.addEntry(OperatorUtils.getOperatorSymbol(operator))
                }
            } else {
                entriesManager.addEntry(OperatorUtils.getOperatorSymbol(operator))
            }
        }

        displayManager.update(operator.symbol)
    }
}