package com.raistlin.turingmachine

import com.raistlin.turingmachine.program.BaseProgram

class Interpreter {

    val line = Line()
    var program: BaseProgram? = null
    var stateIndex = 1
        private set

    fun run(): Boolean {
        var counter = 0
        line.init(program?.input())
        do {
            val command = program?.command(Pair(stateIndex, line.char))
            command?.let {
                line.char = it.symbol
                when (it.direction) {
                    Command.DirectionType.Left -> line.moveLeft()
                    Command.DirectionType.Right -> line.moveRight()
                    else -> {
                    }
                }
                stateIndex = it.state
            }
            if (++counter > program?.maxSteps().let { it } ?: 0) break
        } while (command != null)
        return stateIndex == Command.FINAL_STATE
    }
}