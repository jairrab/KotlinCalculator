@file:Suppress("TooManyFunctions")

/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc

import com.github.jairrab.calc.lib.CalculatorUtility
import com.github.jairrab.calc.lib.controls.ControlProcessor
import com.github.jairrab.calc.lib.controls.entries.EntriesManager
import java.math.BigDecimal

interface Calculator {
    fun press(button: CalculatorButton)
    fun clear()
    fun resetToNumber(number: Double, readyToClear: Boolean = true)
    fun pressOne()
    fun pressTwo()
    fun pressThree()
    fun pressFour()
    fun pressFive()
    fun pressSix()
    fun pressSeven()
    fun pressEight()
    fun pressNine()
    fun pressZero()
    fun pressDecimal()
    fun pressPlus()
    fun pressMinus()
    fun pressMultiply()
    fun pressDivide()
    fun pressPercent()
    fun backSpace()
    fun pressEquals()
    fun setListener(listener: Listener)
    fun getCurrentNumber(): BigDecimal

    fun interface Listener {
        fun onCalculatorUpdate(update: CalculatorUpdate)
    }

    companion object {
        @JvmStatic
        fun getInstance() = getInstance(
            calculatorType = CalculatorType.BASIC_MDAS,
            initialNumber = 0.0,
            readyToClear = true,
            listener = null,
        )

        @JvmStatic
        fun getInstance(listener: Listener) = getInstance(
            calculatorType = CalculatorType.BASIC_MDAS,
            initialNumber = 0.0,
            readyToClear = true,
            listener = listener,
        )

        @JvmStatic
        fun getInstance(
            calculatorType: CalculatorType = CalculatorType.BASIC_MDAS,
            initialNumber: Double = 0.0,
            readyToClear: Boolean = true,
            listener: Listener? = null,
        ): Calculator = CalculatorUtility(
            initialNumber = initialNumber,
            readyToClear = readyToClear,
            controlProcessor = ControlProcessor.getInstance(
                entriesManager = EntriesManager.getInstance(),
                calculatorType = calculatorType,
                listener = listener,
            ),
        )
    }
}
