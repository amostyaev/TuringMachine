package com.raistlin.turingmachine.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.raistlin.turingmachine.Command
import com.raistlin.turingmachine.Interpreter
import com.raistlin.turingmachine.R
import com.raistlin.turingmachine.program.FileProgram
import java.util.*
import kotlin.concurrent.timer

class TuringActivity : AppCompatActivity() {

    private val interpreter = Interpreter()

    private var tape: TapeWidget? = null
    private var table: TableWidget? = null
    private var state: TextView? = null
    private var launchButton: Button? = null

    private var timer: Timer? = null
    private var speed = 500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_turing)

        tape = findViewById(R.id.main_tape)
        table = findViewById(R.id.main_table)
        state = findViewById(R.id.mane_state)
        launchButton = findViewById(R.id.main_button_start)

        findViewById<Button>(R.id.main_button_load).setOnClickListener {
            val program = FileProgram(assets.open(findViewById<EditText>(R.id.main_edit).text.toString()))
            interpreter.program = program
            interpreter.init()
            tape?.showLine(interpreter.lineItems(), interpreter.lineIndex())
            table?.loadProgram(program)
            updateState()
        }

        launchButton?.setOnClickListener {
            if (timer != null) {
                timer?.cancel()
                timer = null
                updateState()
            } else {
                if (interpreter.program != null) {
                    timer = timer("run", true, speed, speed, {
                        val finished = interpreter.step()
                        runOnUiThread {
                            tape?.showLine(interpreter.lineItems(), interpreter.lineIndex())
                            updateState()
                            if (finished) Toast.makeText(baseContext, R.string.info_finished, Toast.LENGTH_SHORT).show()
                        }
                        if (finished) {
                            cancel()
                            timer = null
                        }
                    })
                }
            }
        }

    }

    private fun updateState() {
        val stateIndex = interpreter.stateIndex
        if (stateIndex != Command.FINAL_STATE) {
            state?.text = interpreter.stateIndex.toString()
        } else {
            state?.text = "!"
        }
        launchButton?.text = getString(if (timer == null) R.string.button_start else R.string.button_stop)
    }
}
