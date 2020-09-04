package com.github.jairrab.kotlincalculator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.jairrab.calc.Calculator

class MainActivity : AppCompatActivity(), Calculator.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calc = Calculator.getInstance(this)
        calc.pressOne()
        calc.pressTwo()
        calc.pressPlus()
        calc.pressFour()
        calc.pressFive()
        calc.pressEquals()
    }

    override fun onCalculatorUpdate(key: String?, entries: List<String>, result: Double) {
        Log.v("CALC", "key: $key | entries: $entries | result: $result")
    }
}