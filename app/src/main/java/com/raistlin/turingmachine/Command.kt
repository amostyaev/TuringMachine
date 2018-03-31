package com.raistlin.turingmachine

class Command(val symbol: Char, val direction: DirectionType, val state: Int) {

    enum class DirectionType(val char: Char) {
        Left('L'), Right('R'), No('N');

        fun toChar(): Char = char

        companion object {
            fun valueOf(value: Char): DirectionType? = DirectionType.values().find { it.char == value }
        }
    }

    companion object {
        const val EMPTY_SYMBOL = '_'
        const val FINAL_STATE = 0
    }
}