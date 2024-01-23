/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.mathutils

import java.math.BigDecimal

interface EntriesCalculator {
    fun solve(entries: List<String>): BigDecimal
}
