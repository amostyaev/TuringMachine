package com.raistlin.turingmachine.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.raistlin.turingmachine.Interpreter
import com.raistlin.turingmachine.R
import com.raistlin.turingmachine.program.FileProgram
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private val interpreter = Interpreter()

    private var tape: TapeWidget? = null
    private var table: TableWidget? = null

    private var speed = 500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tape = findViewById(R.id.main_tape)
        table = findViewById(R.id.main_table)

        findViewById<Button>(R.id.main_button_load).setOnClickListener {
            //interpreter.program = PredefinedProgram()
            val program = FileProgram(assets.open(findViewById<EditText>(R.id.main_edit).text.toString()))
            interpreter.program = program
            interpreter.init()
            tape?.showLine(interpreter.lineItems(), interpreter.lineIndex())
            table?.loadProgram(program)
        }

        findViewById<Button>(R.id.main_button_start).setOnClickListener {
            timer("run", true, speed, speed, {
                val finished = interpreter.step()
                runOnUiThread { tape?.showLine(interpreter.lineItems(), interpreter.lineIndex()) }
                if (finished) cancel()
            })
        }
    }
}
