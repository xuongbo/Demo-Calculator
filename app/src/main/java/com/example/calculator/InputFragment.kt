package com.example.calculator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_input.*
import java.text.DecimalFormat

class InputFragment() : Fragment(), View.OnClickListener {
    private val operatorsArray = arrayOf("+", "-", "*", "/")
    private lateinit var mainListener: MainListener

    private var operators: String = ""
    private var inputNumber: String = ""

    var resultNumber =
        fun(number: String): String { return DecimalFormat("###.##").format(number.toDouble()) }

    interface MainListener {
        fun onResultUpdate(string: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    //button.setOnClickListener { this } not working
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonDot.setOnClickListener { onClick(it) }
        buttonPlus.setOnClickListener { onClick(it) }
        buttonClear.setOnClickListener { onClick(it) }
        buttonMinus.setOnClickListener { onClick(it) }
        buttonResult.setOnClickListener { onClick(it) }
        buttonDivide.setOnClickListener { onClick(it) }
        buttonNumber0.setOnClickListener { onClick(it) }
        buttonNumber1.setOnClickListener { onClick(it) }
        buttonNumber2.setOnClickListener { onClick(it) }
        buttonNumber3.setOnClickListener { onClick(it) }
        buttonNumber4.setOnClickListener { onClick(it) }
        buttonNumber5.setOnClickListener { onClick(it) }
        buttonNumber6.setOnClickListener { onClick(it) }
        buttonNumber7.setOnClickListener { onClick(it) }
        buttonNumber8.setOnClickListener { onClick(it) }
        buttonNumber9.setOnClickListener { onClick(it) }
        buttonMultiply.setOnClickListener { onClick(it) }
        buttonBackspace.setOnClickListener { onClick(it) }
        buttonPercentage.setOnClickListener { onClick(it) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainListener) {
            mainListener = context
        } else {
            throw RuntimeException(context.toString())
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonNumber0 -> getNumberFromButton(buttonNumber0.text.toString())
            R.id.buttonNumber1 -> getNumberFromButton(buttonNumber1.text.toString())
            R.id.buttonNumber2 -> getNumberFromButton(buttonNumber2.text.toString())
            R.id.buttonNumber3 -> getNumberFromButton(buttonNumber3.text.toString())
            R.id.buttonNumber4 -> getNumberFromButton(buttonNumber4.text.toString())
            R.id.buttonNumber5 -> getNumberFromButton(buttonNumber5.text.toString())
            R.id.buttonNumber6 -> getNumberFromButton(buttonNumber6.text.toString())
            R.id.buttonNumber7 -> getNumberFromButton(buttonNumber7.text.toString())
            R.id.buttonNumber8 -> getNumberFromButton(buttonNumber8.text.toString())
            R.id.buttonNumber9 -> getNumberFromButton(buttonNumber9.text.toString())

            //Operators
            R.id.buttonPlus -> checkContain('+')
            R.id.buttonMinus -> checkContain('-')
            R.id.buttonMultiply -> checkContain('*')
            R.id.buttonDivide -> checkContain('/')

            //Other functions
            R.id.buttonPercentage -> {
                inputNumber =
                    if (inputNumber != "") (inputNumber.toFloat() / 100).toString() else "0"
//                (activity as MainActivity).textResult.text = inputNumber
                mainListener.onResultUpdate(inputNumber)
            }
            R.id.buttonClear -> {
                inputNumber = ""
                mainListener.onResultUpdate(inputNumber)
            }
            R.id.buttonResult -> {
                var check = true
                for (string in operatorsArray) {
                    if (inputNumber.substring(inputNumber.length - 1) == string) check = false
                }
                if (check) {
                    try {
                        calculation()
                    } catch (e: Exception) {
                        inputNumber = ""
                        mainListener.onResultUpdate(inputNumber)
                        Toast.makeText(
                            context,
                            getString(R.string.error_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    operators = ""
                }
            }
            R.id.buttonBackspace -> {
                if (inputNumber != "")
                    inputNumber = inputNumber.substring(0, inputNumber.length - 1)
                mainListener.onResultUpdate(inputNumber)
            }
            R.id.buttonDot -> {
                if (!inputNumber.contains('.')) {
                    inputNumber = ("$inputNumber.")
                    mainListener.onResultUpdate(inputNumber)
                }
            }
        }
    }

    private fun calculation() {
        when (operators) {
            "+" -> {
                val result = inputNumber.split('+')
                if (result[1] != "") {
                    inputNumber =
                        (result[0].toDouble() + result[1].toDouble()).toString()
                    operators = ""
                }
                inputNumber = resultNumber(inputNumber)
                mainListener.onResultUpdate(inputNumber)

            }
            "-" -> {
                val result = inputNumber.split('-')
                if (result[1] != "") {
                    inputNumber =
                        (result[0].toDouble() - result[1].toDouble()).toString()
                    operators = ""
                }
                inputNumber = resultNumber(inputNumber)
                mainListener.onResultUpdate(inputNumber)
            }
            "*" -> {
                val result = inputNumber.split('*')
                if (result[1] != "") {
                    inputNumber =
                        (result[0].toDouble() * result[1].toDouble()).toString()
                    operators = ""
                }
                inputNumber = resultNumber(inputNumber)
                mainListener.onResultUpdate(inputNumber)
            }
            "/" -> {
                checkContain('/')
                val result = inputNumber.split('/')
                if (result[1] != "") {
                    inputNumber =
                        (result[0].toDouble() / result[1].toDouble()).toString()
                    operators = ""
                }
                inputNumber = resultNumber(inputNumber)
                mainListener.onResultUpdate(inputNumber)
            }
            else -> {
                inputNumber = ""
                mainListener.onResultUpdate(inputNumber)
            }
        }
    }

    private fun checkContain(char: Char) {
        if (!inputNumber.contains(char) && inputNumber != "") {
            operators = char.toString()
            inputNumber += char.toString()
            mainListener.onResultUpdate(inputNumber)
        } else {
            try {
                calculation()
            } catch (e: Exception) {
                inputNumber = ""
                mainListener.onResultUpdate(inputNumber)
                Toast.makeText(context, getString(R.string.error_message), Toast.LENGTH_SHORT)
                    .show()
                inputNumber = ""
            }
        }
    }

    private fun getNumberFromButton(string: String) {
        inputNumber = (inputNumber + string)
        mainListener.onResultUpdate(inputNumber)
    }
}

