/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.buttons

import com.github.jairrab.calc.lib.controls.entries.EntriesManager

class ClearProcessor(
    private val entriesManager: EntriesManager
) {
    fun initialize(initialNumber: Double) {
        entriesManager.clear()
        if (initialNumber != 0.0) {
            val entry = if (initialNumber % 1 == 0.0) {
                initialNumber.toInt().toString()
            } else {
                initialNumber.toString()
            }

            entriesManager.addEntry(entry)
        }
    }

    fun onCleared() {
        entriesManager.clearLastResult()
    }
}