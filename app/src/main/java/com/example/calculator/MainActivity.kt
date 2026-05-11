package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private var currentInput = StringBuilder()
    private var operator = ""
    private var firstOperand = 0.0
    private var isNewInput = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)

        val numButtons = listOf(
            R.id.btn0 to "0", R.id.btn1 to "1", R.id.btn2 to "2",
            R.id.btn3 to "3", R.id.btn4 to "4", R.id.btn5 to "5",
            R.id.btn6 to "6", R.id.btn7 to "7", R.id.btn8 to "8",
            R.id.btn9 to "9", R.id.btnDot to "."
        )
        numButtons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener { appendInput(value) }
        }

        listOf(R.id.btnAdd to "+", R.id.btnSub to "-",
            R.id.btnMul to "×", R.id.btnDiv to "÷").forEach { (id, op) ->
            findViewById<Button>(id).setOnClickListener { setOperator(op) }
        }

        findViewById<Button>(R.id.btnEquals).setOnClickListener { calculate() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clear() }
        findViewById<Button>(R.id.btnSign).setOnClickListener { toggleSign() }
        findViewById<Button>(R.id.btnPercent).setOnClickListener { percent() }
    }

    private fun appendInput(value: String) {
        if (isNewInput) { currentInput.clear(); isNewInput = false }
        if (value == "." && currentInput.contains(".")) return
        currentInput.append(value)
        tvDisplay.text = currentInput
    }

    private fun setOperator(op: String) {
        firstOperand = currentInput.toString().toDoubleOrNull() ?: 0.0
        operator = op
        isNewInput = true
    }

    private fun calculate() {
        val second = currentInput.toString().toDoubleOrNull() ?: return
        val result = when (operator) {
            "+" -> firstOperand + second
            "-" -> firstOperand - second
            "×" -> firstOperand * second
            "÷" -> if (second != 0.0) firstOperand / second else Double.NaN
            else -> second
        }
        val formatted = if (result % 1 == 0.0) result.toLong().toString() else result.toString()
        tvDisplay.text = formatted
        currentInput = StringBuilder(formatted)
        isNewInput = true
    }

    private fun clear() {
        currentInput.clear(); operator = ""; firstOperand = 0.0
        tvDisplay.text = "0"
    }

    private fun toggleSign() {
        val v = currentInput.toString().toDoubleOrNull() ?: return
        val toggled = (v * -1)
        val s = if (toggled % 1 == 0.0) toggled.toLong().toString() else toggled.toString()
        currentInput = StringBuilder(s); tvDisplay.text = s
    }

    private fun percent() {
        val v = currentInput.toString().toDoubleOrNull() ?: return
        val p = v / 100
        val s = if (p % 1 == 0.0) p.toLong().toString() else p.toString()
        currentInput = StringBuilder(s); tvDisplay.text = s
    }
}