/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib

import com.github.jairrab.calc.Calculator
import com.github.jairrab.calc.CalculatorButton
import com.github.jairrab.calc.lib.controls.ControlProcessor

internal open class CalculatorUtility(
    initialNumber: Double,
    private val controlProcessor: ControlProcessor
) : Calculator {
    init {
        controlProcessor.clearProcessor.initialize(initialNumber)
        controlProcessor.displayManager.update(null)
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
        controlProcessor.displayManager.update(CalculatorButton.CLEAR)
    }

    override fun pressOne() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.ONE)
        controlProcessor.displayManager.update(CalculatorButton.ONE)
    }

    override fun pressTwo() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.TWO)
        controlProcessor.displayManager.update(CalculatorButton.TWO)
    }

    override fun pressThree() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.THREE)
        controlProcessor.displayManager.update(CalculatorButton.THREE)
    }

    override fun pressFour() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.FOUR)
        controlProcessor.displayManager.update(CalculatorButton.FOUR)
    }

    override fun pressFive() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.FIVE)
        controlProcessor.displayManager.update(CalculatorButton.FIVE)
    }

    override fun pressSix() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.SIX)
        controlProcessor.displayManager.update(CalculatorButton.SIX)
    }

    override fun pressSeven() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.SEVEN)
        controlProcessor.displayManager.update(CalculatorButton.SEVEN)
    }

    override fun pressEight() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.EIGHT)
        controlProcessor.displayManager.update(CalculatorButton.EIGHT)
    }

    override fun pressNine() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.NINE)
        controlProcessor.displayManager.update(CalculatorButton.NINE)
    }

    override fun pressZero() {
        controlProcessor.numberProcessor.processNumber(CalculatorButton.ZERO)
        controlProcessor.displayManager.update(CalculatorButton.ZERO)
    }

    override fun pressDecimal() {
        controlProcessor.decimalProcessor.processDecimal()
        controlProcessor.displayManager.update(CalculatorButton.DECIMAL)
    }

    override fun pressPlus() {
        controlProcessor.operatorProcessor.processOperator(CalculatorButton.PLUS)
        controlProcessor.displayManager.update(CalculatorButton.PLUS)
    }

    override fun pressMinus() {
        controlProcessor.operatorProcessor.processOperator(CalculatorButton.MINUS)
        controlProcessor.displayManager.update(CalculatorButton.MINUS)
    }

    override fun pressMultiply() {
        controlProcessor.operatorProcessor.processOperator(CalculatorButton.MULTIPLY)
        controlProcessor.displayManager.update(CalculatorButton.MULTIPLY)
    }

    override fun pressDivide() {
        controlProcessor.operatorProcessor.processOperator(CalculatorButton.DIVISION)
        controlProcessor.displayManager.update(CalculatorButton.DIVISION)
    }

    override fun pressPercent() {
        controlProcessor.percentProcessor.processPercent()
        controlProcessor.displayManager.update(CalculatorButton.PERCENT)
    }

    override fun backSpace() {
        controlProcessor.backspaceProcessor.onBackSpace()
        controlProcessor.displayManager.update(CalculatorButton.BACKSPACE)
    }

    override fun pressEquals() {
        controlProcessor.operatorProcessor.processOperator(CalculatorButton.EQUALS)
        controlProcessor.displayManager.update(CalculatorButton.EQUALS)
    }

    override fun setListener(listener: Calculator.Listener) {
        controlProcessor.setListener(listener)
    }
}