package com.raistlin.turingmachine.program

import com.raistlin.turingmachine.Command

abstract class BaseProgram {

    abstract val input: String

    val statesCount: Int
        get() {
            if (!counted) scanCommands()
            return states.size
        }

    val symbolsCount: Int
        get() {
            if (!counted) scanCommands()
            return symbols.size
        }

    var states = mutableSetOf<Int>()
    var symbols = mutableSetOf<Char>()

    private var counted = false
    private val commands = mutableMapOf<Pair<Int, Char>, Command>()

    fun addCommand(state: Pair<Int, Char>, command: Command) {
        counted = false
        commands[state] = command
    }

    fun command(state: Pair<Int, Char>): Command? {
        return commands[state]
    }

    private fun scanCommands() {
        for (key in commands.keys) {
            states.add(key.first)
            symbols.add(key.second)
        }
        counted = true
    }

    open fun maxSteps(): Int {
        return 1000
    }
}