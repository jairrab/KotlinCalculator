@file:Suppress("TooManyFunctions")

/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib

import com.github.jairrab.calc.Calculator
import com.github.jairrab.calc.CalculatorButton
import com.github.jairrab.calc.lib.controls.ControlProcessor

internal open class CalculatorUtility(
    initialNumber: Double,
    readyToClear: Boolean,
    private val controlProcessor: ControlProcessor,
) : Calculator {
    init {
        resetToNumber(initialNumber, readyToClear)
    }

    override fun press(button: CalculatorButton) {
        when (button) {
            CalculatorButton.CLEAR -> clear()
            CalculatorButton.ONE -> pressOne()
            CalculatorButton.TWO -> pressTwo()
            CalculatorButton.THREE -> pressThree()
            CalculatorButton.FOUR -> pressFour()
            CalculatorButton.FIVE -> pressFive()
            CalculatorButton.SIX -> pressSix()
            CalculatorButton.SEVEN -> pressSeven()
            CalculatorButton.EIGHT -> pressEight()
            CalculatorButton.NINE -> pressNine()
            CalculatorButton.ZERO -> pressZero()
            CalculatorButton.DECIMAL -> pressDecimal()
            CalculatorButton.PLUS -> pressPlus()
            CalculatorButton.MINUS -> pressMinus()
            CalculatorButton.MULTIPLY -> pressMultiply()
            CalculatorButton.DIVISION -> pressDivide()
            CalculatorButton.BACKSPACE -> backSpace()
            CalculatorButton.PERCENT -> pressPercent()
            CalculatorButton.EQUALS -> pressEquals()
        }
    }

    override fun clear() {
        controlProcessor.clearProcessor.onCleared()
        controlProcessor.outputManager.update(CalculatorButton.CLEAR)
    }

    final override fun resetToNumber(number: Double, readyToClear: Boolean) {
        controlProcessor.clearProcessor.initialize(number, readyToClear)
        controlProcessor.outputManager.update(number)
    }

    override fun pressOne() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.ONE)
        controlProcessor.outputManager.update(CalculatorButton.ONE)
    }

    override fun pressTwo() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.TWO)
        controlProcessor.outputManager.update(CalculatorButton.TWO)
    }

    override fun pressThree() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.THREE)
        controlProcessor.outputManager.update(CalculatorButton.THREE)
    }

    override fun pressFour() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.FOUR)
        controlProcessor.outputManager.update(CalculatorButton.FOUR)
    }

    override fun pressFive() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.FIVE)
        controlProcessor.outputManager.update(CalculatorButton.FIVE)
    }

    override fun pressSix() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.SIX)
        controlProcessor.outputManager.update(CalculatorButton.SIX)
    }

    override fun pressSeven() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.SEVEN)
        controlProcessor.outputManager.update(CalculatorButton.SEVEN)
    }

    override fun pressEight() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.EIGHT)
        controlProcessor.outputManager.update(CalculatorButton.EIGHT)
    }

    override fun pressNine() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.NINE)
        controlProcessor.outputManager.update(CalculatorButton.NINE)
    }

    override fun pressZero() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.ZERO)
        controlProcessor.outputManager.update(CalculatorButton.ZERO)
    }

    override fun pressDecimal() {
        controlProcessor.decimalProcessor.processDecimal()
        controlProcessor.outputManager.update(CalculatorButton.DECIMAL)
    }

    override fun pressPlus() {
        controlProcessor.operatorProcessor.processOperator(CalculatorButton.PLUS)
        controlProcessor.outputManager.update(CalculatorButton.PLUS)
    }

    override fun pressMinus() {
        controlProcessor.operatorProcessor.processOperator(CalculatorButton.MINUS)
        controlProcessor.outputManager.update(CalculatorButton.MINUS)
    }

    override fun pressMultiply() {
        controlProcessor.operatorProcessor.processOperator(CalculatorButton.MULTIPLY)
        controlProcessor.outputManager.update(CalculatorButton.MULTIPLY)
    }

    override fun pressDivide() {
        controlProcessor.operatorProcessor.processOperator(CalculatorButton.DIVISION)
        controlProcessor.outputManager.update(CalculatorButton.DIVISION)
    }

    override fun pressPercent() {
        controlProcessor.percentProcessor.processPercent()
        controlProcessor.outputManager.update(CalculatorButton.PERCENT)
    }

    override fun backSpace() {
        controlProcessor.backspaceProcessor.onBackSpace()
        controlProcessor.outputManager.update(CalculatorButton.BACKSPACE)
    }

    override fun pressEquals() {
        controlProcessor.operatorProcessor.processOperator(CalculatorButton.EQUALS)
        controlProcessor.outputManager.update(CalculatorButton.EQUALS)
    }

    override fun setListener(listener: Calculator.Listener) {
        controlProcessor.setListener(listener)
    }

    override fun getCurrentNumber(): Double {
        return controlProcessor.outputManager.getCurrentNumber()
    }
}
