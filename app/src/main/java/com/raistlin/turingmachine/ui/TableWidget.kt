package com.raistlin.turingmachine.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.raistlin.turingmachine.Command
import com.raistlin.turingmachine.program.BaseProgram

class TableWidget(context: Context?, attrs: AttributeSet?) : TableLayout(context, attrs) {

    companion object {
        const val DIVIDER_CHAR = ","
        const val FINAL_CHAR = "!"
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
                val view: TextView
                if (i == -1 || j == -1) {
                    view = TextView(context)
                } else {
                    view = EditText(context)
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
                    val command = program.command(Pair(states.elementAt(i), symbols.elementAt(j)))
                    view.text = commandToString(command, states.elementAt(i), symbols.elementAt(j)) ?: ""
                }
            }
            addView(tableRow)
        }
    }

    private fun commandToString(command: Command?, state: Int, symbol: Char): String? {
        if (command != null) {
            val sb = StringBuilder()
            if (command.symbol != Command.EMPTY_SYMBOL && symbol != command.symbol) sb.append(command.symbol)
            sb.append(DIVIDER_CHAR)
            if (command.direction != Command.DirectionType.No) sb.append(command.direction.toChar())
            sb.append(DIVIDER_CHAR)
            if (command.state == Command.FINAL_STATE) {
                sb.append(FINAL_CHAR)
            } else if (command.state != state) {
                sb.append(command.state)
            }
            return sb.toString()
        } else {
            return null
        }
    }

    init {
        isStretchAllColumns = true
    }

}