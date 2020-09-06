/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls

class ClearProcessor(
    private val entriesManager: EntriesManager,
    private val displayManager: DisplayManager
) {
    fun initialize(initialNumber: Double) {
        if (initialNumber != 0.0) {
            val entry = if (initialNumber % 1 == 0.0) {
                initialNumber.toInt().toString()
            } else {
                initialNumber.toString()
            }

            entriesManager.addEntry(entry)
        }

        displayManager.update("initializing")
    }

    fun onCleared() {
        entriesManager.clear()
        displayManager.update("clear")
    }
}