package com.github.jairrab.kotlincalculator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.jairrab.calc.Calculator
import com.github.jairrab.calc.CalculatorButton

class MainActivity : AppCompatActivity(), Calculator.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calculator = Calculator.getInstance(this)

        //call buttons directly
        calculator.pressOne()
        calculator.pressTwo()
        calculator.pressPlus()
        calculator.pressFour()
        calculator.pressFive()
        calculator.pressEquals()

        //use the common press function
        calculator.press(CalculatorButton.CLEAR)
        calculator.press(CalculatorButton.ONE)
        calculator.press(CalculatorButton.TWO)
        calculator.press(CalculatorButton.DECIMAL)
        calculator.press(CalculatorButton.FIVE)
        calculator.press(CalculatorButton.MULTIPLY)
        calculator.press(CalculatorButton.SIX)
        calculator.press(CalculatorButton.ZERO)
        calculator.press(CalculatorButton.BACKSPACE)
        calculator.press(CalculatorButton.EQUALS)
    }

    //listen to the calculator button responses
    override fun onCalculatorUpdate(key: String?, entries: List<String>, result: Double) {
        Log.v("CALC", "key: $key | entries: $entries | result: $result")
    }
}