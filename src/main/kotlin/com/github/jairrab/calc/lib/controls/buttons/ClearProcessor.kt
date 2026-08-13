// This code is licensed under MIT license (see LICENSE.txt for details)

package com.github.jairrab.calc.lib.controls.buttons

import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import java.math.BigDecimal

internal class ClearProcessor(
    private val entriesManager: EntriesManager,
) {
    fun initialize(initialNumber: Double, readyToClear: Boolean) {
        entriesManager.clearEntries()
        entriesManager.setReadyToClear(readyToClear)

        if (initialNumber != 0.0 && !initialNumber.isInfinite() && !initialNumber.isNaN()) {
            // Always seed a plain decimal string, never scientific notation (e.g. Double.toString
            // renders 0.0004 as "4.0E-4"). BigDecimal.valueOf uses Double.toString's shortest
            // round-trip and stripTrailingZeros drops the resulting "0.00040" trailing zero, so
            // 0.0004 seeds as "0.0004" and backspace can't produce an incomplete exponent entry.
            // (BigDecimal(0.0004) would seed the exact binary expansion instead.)
            val entry = BigDecimal.valueOf(initialNumber).stripTrailingZeros().toPlainString()

            entriesManager.addEntry(entry)
            entriesManager.setResult(BigDecimal.valueOf(initialNumber))
        }
    }

    fun onCleared() {
        entriesManager.clearEntries()
        entriesManager.setReadyToClear(false)
    }
}
