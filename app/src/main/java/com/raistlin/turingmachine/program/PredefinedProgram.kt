package com.raistlin.turingmachine.program

import com.raistlin.turingmachine.Command

class PredefinedProgram : BaseProgram {

    private val commands = mapOf(
            Pair(Pair(1, Command.EMPTY_SYMBOL), Command(Command.EMPTY_SYMBOL, Command.DirectionType.No, Command.FINAL_STATE)),
            Pair(Pair(1, 'a'), Command('a', Command.DirectionType.Right, 2)),
            Pair(Pair(1, 'b'), Command(Command.EMPTY_SYMBOL, Command.DirectionType.Right, 1)),
            Pair(Pair(1, 'c'), Command(Command.EMPTY_SYMBOL, Command.DirectionType.Right, 1)),
            Pair(Pair(2, Command.EMPTY_SYMBOL), Command(Command.EMPTY_SYMBOL, Command.DirectionType.No, Command.FINAL_STATE)),
            Pair(Pair(2, 'a'), Command(Command.EMPTY_SYMBOL, Command.DirectionType.Right, 2)),
            Pair(Pair(2, 'b'), Command(Command.EMPTY_SYMBOL, Command.DirectionType.Right, 2)),
            Pair(Pair(2, 'c'), Command(Command.EMPTY_SYMBOL, Command.DirectionType.Right, 2))
    )

    override fun input(): String {
        return "cbcbcbcbcaaabcbcbcbccb"
    }

    override fun command(state: Pair<Int, Char>): Command? {
        return commands[state]
    }

}