@file:Suppress("UseCheckOrError")

// This code is licensed under MIT license (see LICENSE.txt for details)

package com.github.jairrab.calc.lib.utils

import com.github.jairrab.calc.CalculatorButton
import java.math.BigDecimal

/**
 * Parses a calculator entry to [BigDecimal] without throwing.
 *
 * Incomplete scientific-notation entries (e.g. `"1.0E-"`, `"1.0E"`, `"1.0E+"`) and other
 * malformed entries fall back to their mantissa, or [BigDecimal.ZERO], instead of crashing.
 */
internal fun String.toCalculatorBigDecimal(): BigDecimal {
    return when {
        endsWith(CalculatorButton.PERCENT.tag) ->
            BigDecimal(trimEndChar().toDoubleOrNull() ?: 0.0).divide(BigDecimal(100.0))
        endsWith("E-", ignoreCase = true) ->
            BigDecimal(trimEndChar(2).toDoubleOrNull() ?: 0.0)
        endsWith("E+", ignoreCase = true) ->
            BigDecimal(trimEndChar(2).toDoubleOrNull() ?: 0.0)
        endsWith("E", ignoreCase = true) ->
            BigDecimal(trimEndChar().toDoubleOrNull() ?: 0.0)
        toDoubleOrNull() != null -> BigDecimal(this)
        else -> BigDecimal.ZERO
    }
}
