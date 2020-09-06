/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib

import com.github.jairrab.calc.Calculator
import com.github.jairrab.calc.CalculatorButton
import com.github.jairrab.calc.lib.controls.ControlProcessor
import com.github.jairrab.calc.lib.mathutils.DECIMAL
import com.github.jairrab.calc.lib.mathutils.Operator

internal open class CalculatorUtility(
    initialNumber: Double,
    private val controlProcessor: ControlProcessor
) : Calculator {
    init {
        controlProcessor.clearProcessor.initialize(initialNumber)
        controlProcessor.displayManager.update("initializing")
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
            CalculatorButton.EQUALS -> pressEquals()
        }
    }

    override fun clear() {
        controlProcessor.clearProcessor.onCleared()
        controlProcessor.displayManager.update("clear")
    }

    override fun pressOne() {
        controlProcessor.numberProcessor.processNumber(1)
        controlProcessor.displayManager.update("1")
    }

    override fun pressTwo() {
        controlProcessor.numberProcessor.processNumber(2)
        controlProcessor.displayManager.update("2")
    }

    override fun pressThree() {
        controlProcessor.numberProcessor.processNumber(3)
        controlProcessor.displayManager.update("3")
    }

    override fun pressFour() {
        controlProcessor.numberProcessor.processNumber(4)
        controlProcessor.displayManager.update("4")
    }

    override fun pressFive() {
        controlProcessor.numberProcessor.processNumber(5)
        controlProcessor.displayManager.update("5")
    }

    override fun pressSix() {
        controlProcessor.numberProcessor.processNumber(6)
        controlProcessor.displayManager.update("6")
    }

    override fun pressSeven() {
        controlProcessor.numberProcessor.processNumber(7)
        controlProcessor.displayManager.update("7")
    }

    override fun pressEight() {
        controlProcessor.numberProcessor.processNumber(8)
        controlProcessor.displayManager.update("8")
    }

    override fun pressNine() {
        controlProcessor.numberProcessor.processNumber(9)
        controlProcessor.displayManager.update("9")
    }

    override fun pressZero() {
        controlProcessor.numberProcessor.processNumber(0)
        controlProcessor.displayManager.update("0")
    }

    override fun pressDecimal() {
        controlProcessor.decimalProcessor.processDecimal()
        controlProcessor.displayManager.update(DECIMAL)
    }

    override fun pressPlus() {
        controlProcessor.operatorProcessor.processOperator(Operator.Plus)
        controlProcessor.displayManager.update(Operator.Plus.symbol)
    }

    override fun pressMinus() {
        controlProcessor.operatorProcessor.processOperator(Operator.Minus)
        controlProcessor.displayManager.update(Operator.Minus.symbol)
    }

    override fun pressMultiply() {
        controlProcessor.operatorProcessor.processOperator(Operator.Multiply)
        controlProcessor.displayManager.update(Operator.Multiply.symbol)
    }

    override fun pressDivide() {
        controlProcessor.operatorProcessor.processOperator(Operator.Divide)
        controlProcessor.displayManager.update(Operator.Divide.symbol)
    }

    override fun backSpace() {
        controlProcessor.backspaceProcessor.onBackSpace()
        controlProcessor.displayManager.update("backspace")
    }

    override fun pressEquals() {
        controlProcessor.operatorProcessor.processEquals()
        controlProcessor.displayManager.update(Operator.Equals.symbol)
    }
}