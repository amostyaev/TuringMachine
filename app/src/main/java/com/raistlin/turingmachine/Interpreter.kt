package com.raistlin.turingmachine

import com.raistlin.turingmachine.program.BaseProgram

class Interpreter {

    var program: BaseProgram? = null
    var stateIndex = 1
        private set
    private val line = Line()
    private var counter = 0

    fun init() {
        counter = 0
        stateIndex = 1
        line.init(program?.input)
    }

    fun step(): Boolean {
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
        return stateIndex == Command.FINAL_STATE && (++counter <= program?.maxSteps() ?: 0)
    }

    fun updateLine(items: String) {
        line.update(items)
    }

    fun lineItems(): List<Char> {
        val result = mutableListOf<Char>()
        for (i in 0 until line.items.size) {
            result.add(if (line.items[i] == Line.EMPTY) ' ' else line.items[i])
        }
        return result
    }

    fun lineIndex(): Int {
        return line.currentPosition
    }
}