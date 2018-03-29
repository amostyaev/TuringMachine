package com.raistlin.turingmachine.program

import com.raistlin.turingmachine.Command
import java.io.InputStream

class FileProgram(inputStream: InputStream) : BaseProgram {

    companion object {
        const val RROGRAM_END = "cm0"
        const val SYMBOL_EMPTY = '^'
        const val SYMBOL_LEFT = 'L'
        const val SYMBOL_RIGHT = 'R'
        const val SYMBOL_STAY = 'N'
        const val SYMBOL_FINAL = '!'
    }

    private val commands = mutableMapOf<Pair<Int, Char>, Command>()

    override var input = ""
        private set

    override fun command(state: Pair<Int, Char>): Command? {
        return commands[state]
    }

    private fun readTrmFile(inputStream: InputStream) {
        val lines = inputStream.bufferedReader().readLines();
        if (lines.size < 4) return

        val alphabet = lines[0]
        input = lines[1]
        for (lineIndex in 2 until lines.indexOf(RROGRAM_END)) {
            val state = lineIndex - 1
            for (i in 0 until alphabet.length) {
                val command = lines[lineIndex].substring(i * 4, (i + 1) * 4)
                if (!command.isEmpty()) {
                    val symbol = if (alphabet[i] != SYMBOL_EMPTY) alphabet[i] else Command.EMPTY_SYMBOL
                    val symbolTo = if (command[0] != SYMBOL_EMPTY) command[0] else Command.EMPTY_SYMBOL
                    val dir = command[1]
                    if (dir == SYMBOL_FINAL) {
                        commands[Pair(state, symbol)] = Command(symbolTo, Command.DirectionType.No, Command.FINAL_STATE)
                    } else {
                        val direction = parseDirection(dir)
                        val stateChar = command[2]
                        val stateTo = when {
                            stateChar.isDigit() -> stateChar - '0'
                            stateChar.isLetter() -> stateChar.toLowerCase() - 'a'
                            else -> state
                        }
                        commands[Pair(state, symbol)] = Command(symbolTo, direction, stateTo)
                    }
                }
            }
        }
    }

    private fun parseDirection(char: Char): Command.DirectionType {
        return when (char) {
            SYMBOL_LEFT -> Command.DirectionType.Left
            SYMBOL_RIGHT -> Command.DirectionType.Right
            SYMBOL_STAY -> Command.DirectionType.No
            else -> Command.DirectionType.No
        }
    }

    init {
        readTrmFile(inputStream)
    }

}