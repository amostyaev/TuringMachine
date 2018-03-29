package com.raistlin.turingmachine

class Line {

    companion object {
        const val EMPTY = '_'
    }

    var items = mutableListOf<Char>()
    var currentPosition = 0
        private set
    var char
        get() = getCurrentChar()
        set(value) {
            putCurrentChar(value)
        }

    fun init(input: String?) {
        for (c in input.orEmpty()) {
            items.add(c)
        }
    }

    fun moveLeft() {
        --currentPosition
    }

    fun moveRight() {
        ++currentPosition
    }

    private fun putCurrentChar(char: Char) {
        if (currentPosition < 0) {
            while (++currentPosition < 0) {
                items.add(0, EMPTY)
            }
            items.add(currentPosition, char)
        } else if (currentPosition > items.size) {
            while (currentPosition + 1 > items.size) {
                items.add(EMPTY);
            }
            items.add(currentPosition, char)
        } else {
            items.add(currentPosition, char)
            if (currentPosition + 1 < items.size) {
                items.removeAt(currentPosition + 1)
            }
        }
    }

    private fun getCurrentChar(): Char {
        if (currentPosition < 0 || currentPosition >= items.size) {
            return EMPTY
        } else {
            return items[currentPosition]
        }
    }
}