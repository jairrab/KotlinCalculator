// This code is licensed under MIT license (see LICENSE.txt for details)

package com.github.jairrab.calc.lib

import com.github.jairrab.calc.Calculator
import com.github.jairrab.calc.CalculatorButton
import com.github.jairrab.calc.CalculatorType
import com.github.jairrab.calc.CalculatorUpdate
import com.github.jairrab.calc.lib.controls.buttons.BackspaceProcessor
import com.github.jairrab.calc.lib.controls.buttons.DecimalProcessor
import com.github.jairrab.calc.lib.controls.buttons.NumberProcessor
import com.github.jairrab.calc.lib.controls.buttons.OperatorProcessor
import com.github.jairrab.calc.lib.controls.buttons.PercentProcessor
import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import com.github.jairrab.calc.lib.controls.outputs.OutputManager
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Regression tests for the Crashlytics issue where opening the calculator from an amount
 * holding a small/large fractional value (e.g. 0.0004), pressing backspace once, then
 * pressing percent crashed with `IllegalStateException: Invalid operator entry`.
 *
 * Root cause: seeding used `Double.toString()`, which renders 0.0004 as `"4.0E-4"`.
 * Backspace truncated it to the incomplete exponent `"4.0E-"`, which no processor branch
 * understood, so `%` fell through to a `throw`.
 */
class CalculatorIncompleteExponentTest {

    private fun lastUpdate(
        updates: MutableList<CalculatorUpdate>,
    ): CalculatorUpdate.OnUpdate {
        return updates.filterIsInstance<CalculatorUpdate.OnUpdate>().last()
    }

    @Test
    fun `seeds a small fractional amount as a plain decimal entry`() {
        val updates = mutableListOf<CalculatorUpdate>()
        Calculator.getInstance(initialNumber = 0.0004, listener = { updates += it })
            .backSpace()

        // Part 1: the seeded entry must be plain "0.0004", so backspace yields "0.000" —
        // never scientific notation ("4.0E-4" -> "4.0E-"), which is unparseable.
        assertEquals("0.000", lastUpdate(updates).resultText)
    }

    @Test
    fun `reported crash - open small amount, backspace, percent - does not throw`() {
        val calculator = Calculator.getInstance(initialNumber = 0.0004)

        calculator.backSpace()

        assertDoesNotThrow { calculator.pressPercent() }
    }

    @Test
    fun `percent on an incomplete exponent entry does not throw`() {
        val entriesManager = EntriesManager.getInstance()
        entriesManager.addEntry("1.0E-")
        val outputManager = OutputManager.getInstance(entriesManager, CalculatorType.BASIC_MDAS, null)
        val percentProcessor = PercentProcessor(entriesManager, outputManager)

        assertDoesNotThrow { percentProcessor.processPercent() }
    }

    @Test
    fun `operator digit decimal and backspace on an incomplete exponent entry do not throw`() {
        val entriesManager = EntriesManager.getInstance()
        entriesManager.addEntry("1.0E-")
        val outputManager = OutputManager.getInstance(entriesManager, CalculatorType.BASIC_MDAS, null)

        assertDoesNotThrow { OperatorProcessor(entriesManager, outputManager).processOperator(CalculatorButton.PLUS) }
        assertDoesNotThrow { NumberProcessor(entriesManager).processNumber(CalculatorButton.ONE) }
        assertDoesNotThrow { DecimalProcessor(entriesManager, outputManager).processDecimal() }
        assertDoesNotThrow { BackspaceProcessor(entriesManager).onBackSpace() }
    }

    @Test
    fun `output update does not throw for unparseable single or multi entry states`() {
        // Single entry ending with "E+" is not a recognized exponent suffix.
        val single = EntriesManager.getInstance()
        single.addEntry("1.0E+")
        val singleOutput = OutputManager.getInstance(single, CalculatorType.BASIC_MDAS, null)
        assertDoesNotThrow { singleOutput.update(CalculatorButton.PERCENT) }

        // Multi-entry expression containing an incomplete exponent.
        val multi = EntriesManager.getInstance()
        multi.addEntry("100")
        multi.addEntry(CalculatorButton.PLUS.tag)
        multi.addEntry("1.0E-")
        val multiOutput = OutputManager.getInstance(multi, CalculatorType.BASIC_MDAS, null)
        assertDoesNotThrow { multiOutput.update(CalculatorButton.PERCENT) }
    }

    @Test
    fun `valid percent still works`() {
        val updates = mutableListOf<CalculatorUpdate>()
        // readyToClear = false so % applies to the seeded entry instead of being a no-op.
        Calculator.getInstance(initialNumber = 100.0, readyToClear = false, listener = { updates += it })
            .pressPercent()

        assertEquals(1.0, lastUpdate(updates).result.toDouble())
    }
}
