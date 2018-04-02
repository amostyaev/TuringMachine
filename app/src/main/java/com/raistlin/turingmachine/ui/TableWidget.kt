package com.raistlin.turingmachine.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.raistlin.turingmachine.Command
import com.raistlin.turingmachine.program.BaseProgram

class TableWidget(context: Context?, attrs: AttributeSet?) : TableLayout(context, attrs) {

    companion object {
        const val DIVIDER_CHAR = ','
        const val FINAL_CHAR = '!'
    }

    private var program: BaseProgram? = null

    fun loadProgram(program: BaseProgram) {
        removeAllViews()
        this.program = program
        val states = program.states
        val symbols = program.symbols
        for (i in -1 until program.statesCount) {
            val tableRow = TableRow(context)
            tableRow.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, 1.0f)
            for (j in -1 until program.symbolsCount) {
                val view: TextView = if (i == -1 || j == -1) {
                    TextView(context)
                } else {
                    EditText(context)
                }
                view.gravity = Gravity.CENTER
                view.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)
                tableRow.addView(view)
                if (i == -1 && j == -1) {
                    continue
                } else if (i == -1) {
                    view.text = symbols.elementAt(j).toString()
                } else if (j == -1) {
                    view.text = states.elementAt(i).toString()
                } else {
                    val state = states.elementAt(i)
                    val symbol = symbols.elementAt(j)
                    view.text = commandToString(program.command(Pair(state, symbol)), state, symbol) ?: ""
                    view.setSingleLine(true)
                    view.imeOptions = EditorInfo.IME_ACTION_DONE
                    view.setOnEditorActionListener { textView: TextView, actionId: Int, _: KeyEvent? ->
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            program.addCommand(Pair(state, symbol), stringToCommand(textView.text.toString(), state, symbol))
                        }
                        false
                    }
                }
            }
            addView(tableRow)
        }
    }

    private fun commandToString(command: Command?, state: Int, symbol: Char): String? {
        if (command != null) {
            val sb = StringBuilder()
            if (symbol != command.symbol) sb.append(command.symbol)
            sb.append(DIVIDER_CHAR)
            if (command.direction != Command.DirectionType.No) sb.append(command.direction.toChar())
            sb.append(DIVIDER_CHAR)
            sb.append(when (command.state) {
                Command.FINAL_STATE -> FINAL_CHAR
                state -> ""
                in 1..9 -> command.state
                in 10..100 -> 'A' + command.state - 10
                else -> ""
            })
            return sb.toString()
        } else {
            return null
        }
    }

    private fun stringToCommand(value: String, state: Int, symbol: Char): Command {
        val cms = value.split(DIVIDER_CHAR)
        return try {
            val sym = if (!cms[0].isEmpty()) cms[0][0] else symbol
            val dir = Command.DirectionType.valueOf(cms[1][0]) ?: Command.DirectionType.No
            val st = if (!cms[2].isEmpty()) when (cms[2][0]) {
                in '0'..'9' -> cms[2][0] - '0'
                in 'a'..'z' -> cms[2][0] - 'a'
                in 'A'..'Z' -> cms[2][0] - 'A'
                FINAL_CHAR -> Command.FINAL_STATE
                else -> state
            } else state
            Command(sym, dir, st)
        } catch (e: IndexOutOfBoundsException) {
            Command(symbol, Command.DirectionType.No, state)
        }
    }

    init {
        isStretchAllColumns = true
    }

}