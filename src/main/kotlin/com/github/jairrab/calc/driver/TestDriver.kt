/*
 * Copyright (C) 2020 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Antonio Barria <jaybarria@gmail.com>
 * Last modified 9/6/20 7:25 PM
 */

package com.github.jairrab.calc.driver

import com.github.jairrab.calc.Calculator
import com.github.jairrab.calc.CalculatorType

fun main() {
    val calculator = Calculator.getInstance(
        calculatorType = CalculatorType.BASIC_MDAS,
        initialNumber = 0.0,
    ) {
        // See runtime logs for output
    }

    calculator.pressOne()
    calculator.pressTwo()
    calculator.pressThree()
    calculator.pressMultiply()
    calculator.pressFour()
    calculator.pressFive()
    calculator.pressSix()
    calculator.backSpace()
    calculator.pressEquals()
}
