/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.entries

import com.github.jairrab.calc.CalculatorButton
import com.github.jairrab.calc.CalculatorButton.DECIMAL
import com.github.jairrab.calc.lib.mathutils.OperatorUtils.operatorTags
import com.github.jairrab.calc.lib.utils.trimEndChar

internal class EntriesManager private constructor() {
    private val entries: MutableList<String> = ArrayList()
    private var result: Double? = null
    private var readyToClear = false

    fun getEntries(): List<String> {
        return entries
    }

    fun clearEntries() {
        entries.clear()
    }

    fun isReadyToClear(): Boolean {
        return readyToClear
    }

    fun setReadyToClear(readyToClear: Boolean) {
        this.readyToClear = readyToClear
    }

    fun getResult(): Double {
        return result ?: 0.0
    }

    fun setResult(result: Double) {
        this.result = result
    }

    fun addEntry(entry: String) {
        entries += entry
    }

    fun isNoEntries(): Boolean {
        return entries.isEmpty()
    }

    fun hasEntries(): Boolean {
        return entries.isNotEmpty()
    }

    fun isSingleEntry(): Boolean {
        return entries.size == 1
    }

    fun removeLastEntry() {
        entries.removeAt(entries.lastIndex)
    }

    fun setLastEntry(entry: String) {
        entries[entries.lastIndex] = entry
    }

    fun appendToLastEntry(text: String) {
        setLastEntry(getLastEntry() + text)
    }

    fun getLastEntry(): String {
        return entries.last()
    }

    fun getLastDoubleEntry(): Double {
        val lastEntry = getLastEntry()
        return if (lastEntry.endsWith(CalculatorButton.PERCENT.tag)) {
            lastEntry.trimEndChar().toDouble() / 100.0
        } else {
            lastEntry.toDouble()
        }
    }

    fun isLastEntryADecimal(): Boolean {
        return getLastEntry() == DECIMAL.tag
    }

    fun isLastEntryAnOperator(): Boolean {
        return operatorTags.contains(getLastEntry())
    }

    fun isLastEntryAPercentNumber(): Boolean {
        return (getLastEntry().endsWith(CalculatorButton.PERCENT.tag))
    }

    fun isLastEntryANumber(): Boolean {
        return getLastEntry().toDoubleOrNull() != null
    }

    fun isLastEntryANumberWithDecimal(): Boolean {
        return if (isLastEntryANumber() || isLastEntryAPercentNumber()) {
            getLastEntry().contains(DECIMAL.tag)
        } else false
    }

    fun isLastEntryEndsWithDecimal(): Boolean {
        return getLastEntry().endsWith(DECIMAL.tag)
    }

    companion object {
        fun getInstance(): EntriesManager {
            return EntriesManager()
        }
    }
}