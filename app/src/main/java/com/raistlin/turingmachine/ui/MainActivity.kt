package com.raistlin.turingmachine.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.raistlin.turingmachine.Interpreter
import com.raistlin.turingmachine.R
import com.raistlin.turingmachine.program.PredefinedProgram

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            val interpreter = Interpreter()
            interpreter.program = PredefinedProgram()
            interpreter.run()
            Toast.makeText(baseContext, interpreter.line.items.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
