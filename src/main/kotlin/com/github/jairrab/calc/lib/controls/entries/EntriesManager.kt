/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.controls.entries

import com.github.jairrab.calc.CalculatorButton.DECIMAL
import com.github.jairrab.calc.lib.mathutils.OperatorUtils.operatorTags

class EntriesManager private constructor() {
    var lastResult: Double? = null

    private val entries: MutableList<String> = ArrayList()

    fun getEntries(): List<String> {
        return entries
    }

    fun clear() {
        entries.clear()
    }

    fun addEntry(entry: String) {
        entries += entry
    }

    fun clearLastResult() {
        clear()
        lastResult = null
    }

    fun setToLastResult() {
        entries.clear()
        entries += lastResult.toString()
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

    fun getLastEntry(): String {
        return entries.last()
    }

    fun isLastEntryADecimal(): Boolean {
        return getLastEntry() == DECIMAL.tag
    }

    fun isLastEntryAnOperator(): Boolean {
        return operatorTags.contains(getLastEntry())
    }

    fun isLastEntryANumber(): Boolean {
        return !isLastEntryAnOperator() && !isLastEntryADecimal()
    }

    fun isLastEntryANumberWithDecimal(): Boolean {
        return if (isLastEntryANumber()) {
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