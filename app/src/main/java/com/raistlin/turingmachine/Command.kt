package com.raistlin.turingmachine

class Command(val symbol: Char, val direction: DirectionType, val state: Int) {

    enum class DirectionType {
        Left, Right, No
    }

    companion object {
        const val EMPTY_SYMBOL = '_'
        const val FINAL_STATE = 0
    }
}