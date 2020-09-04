/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib

import com.github.jairrab.calc.Calculator
import com.github.jairrab.calc.lib.controls.*
import com.github.jairrab.calc.lib.mathutils.Operator

internal open class CalculatorUtility(
    initialNumber: Double,
    private val backspaceProcessor: BackspaceProcessor,
    private val clearProcessor: ClearProcessor,
    private val decimalProcessor: DecimalProcessor,
    private val numberProcessor: NumberProcessor,
    private val operationProcessor: OperationProcessor
) : Calculator {
    init {
        clearProcessor.initialize(initialNumber)
    }

    override fun clear() = clearProcessor.onCleared()

    override fun pressOne() = numberProcessor.processNumber(1)

    override fun pressTwo() = numberProcessor.processNumber(2)

    override fun pressThree() = numberProcessor.processNumber(3)

    override fun pressFour() = numberProcessor.processNumber(4)

    override fun pressFive() = numberProcessor.processNumber(5)

    override fun pressSix() = numberProcessor.processNumber(6)

    override fun pressSeven() = numberProcessor.processNumber(7)

    override fun pressEight() = numberProcessor.processNumber(8)

    override fun pressNine() = numberProcessor.processNumber(9)

    override fun pressZero() = numberProcessor.processNumber(0)

    override fun pressDecimal() = decimalProcessor.processDecimal()

    override fun pressPlus() = operationProcessor.processOperator(Operator.Plus)

    override fun pressMinus() = operationProcessor.processOperator(Operator.Minus)

    override fun pressMultiply() = operationProcessor.processOperator(Operator.Multiply)

    override fun pressDivide() = operationProcessor.processOperator(Operator.Divide)

    override fun backSpace() = backspaceProcessor.onBackSpace()

    override fun pressEquals() = operationProcessor.processEquals()
}