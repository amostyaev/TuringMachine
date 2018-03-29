package com.raistlin.turingmachine.program

import com.raistlin.turingmachine.Command

interface BaseProgram {

    val input: String

    fun command(state: Pair<Int, Char>): Command?

    fun maxSteps(): Int {
        return 1000
    }
}