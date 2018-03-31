package com.raistlin.turingmachine.program

import com.raistlin.turingmachine.Command

class PredefinedProgram : BaseProgram() {

    override val input = "cbcbcbcbcaaabcbcbcbccb"

    init {
        addCommand(Pair(1, Command.EMPTY_SYMBOL), Command(Command.EMPTY_SYMBOL, Command.DirectionType.No, Command.FINAL_STATE))
        addCommand(Pair(1, 'a'), Command('a', Command.DirectionType.Right, 2))
        addCommand(Pair(1, 'b'), Command(Command.EMPTY_SYMBOL, Command.DirectionType.Right, 1))
        addCommand(Pair(1, 'c'), Command(Command.EMPTY_SYMBOL, Command.DirectionType.Right, 1))
        addCommand(Pair(2, Command.EMPTY_SYMBOL), Command(Command.EMPTY_SYMBOL, Command.DirectionType.No, Command.FINAL_STATE))
        addCommand(Pair(2, 'a'), Command(Command.EMPTY_SYMBOL, Command.DirectionType.Right, 2))
        addCommand(Pair(2, 'b'), Command(Command.EMPTY_SYMBOL, Command.DirectionType.Right, 2))
        addCommand(Pair(2, 'c'), Command(Command.EMPTY_SYMBOL, Command.DirectionType.Right, 2))
    }

}