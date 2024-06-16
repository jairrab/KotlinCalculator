/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.utils

fun String.trimEndChar(numChars: Int = 1): String {
    return this.substring(0, (this.length - numChars).coerceAtLeast(0))
}
