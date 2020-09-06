/* This code is licensed under MIT license (see LICENSE.txt for details) */

package com.github.jairrab.calc.lib.utils


fun List<String>.splitList(delimiter: String): List<List<String>> {
    val a: MutableList<List<String>> = ArrayList()
    val b: MutableList<String> = ArrayList()

    for (i in this) {
        if (i == delimiter) {
            if (b.isNotEmpty()) {
                a.add(ArrayList(b))
            }
            b.clear()
        } else {
            b.add(i)
        }
    }

    if (b.isNotEmpty()) a.add(b)

    return a
}