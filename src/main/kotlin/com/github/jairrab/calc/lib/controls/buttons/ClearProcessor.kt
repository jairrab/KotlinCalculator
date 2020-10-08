/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.buttons

import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import java.math.BigDecimal

internal class ClearProcessor(
    private val entriesManager: EntriesManager
) {
    fun initialize(initialNumber: Double, readyToClear: Boolean) {
        entriesManager.clearEntries()
        entriesManager.setReadyToClear(readyToClear)

        if (initialNumber != 0.0) {
            val entry = if (initialNumber % 1 == 0.0) {
                BigDecimal(initialNumber).setScale(0).toString()
            } else {
                initialNumber.toString()
            }

            entriesManager.addEntry(entry)
            entriesManager.setResult(initialNumber)
        }
    }

    fun onCleared() {
        entriesManager.clearEntries()
        entriesManager.setReadyToClear(false)
    }
}