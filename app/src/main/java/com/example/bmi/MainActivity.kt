package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weightText = findViewById<EditText>(R.id.etWeight)
        val heightText = findViewById<EditText>(R.id.etHeight)
        val calcButton = findViewById<Button>(R.id.button)

        calcButton.setOnClickListener {
            val weight = weightText.text.toString()
            val height = heightText.text.toString()
            if (validateInput(weight,height)){
            val bmi = weight.toFloat()/((height.toFloat()/100) * (height.toFloat()/100))
            val bmi2digit = String.format("%.2f",bmi)

            /* Took int and float parts of bmi separately because String.format was returning
             string with , which cannot be converted into Double or Float */
            val F = bmi2digit.substringBefore(',').toInt()
            val S = bmi2digit.substringAfter(',').toDouble()
            val bmiResult = F + S/100
            displayResult(bmiResult)
            }
        }
    }

    private fun validateInput(weight : String?, height :String?) : Boolean{
    return when{
        weight.isNullOrEmpty() -> {
            Toast.makeText(this,"Weight is empty",Toast.LENGTH_LONG).show()
            return false
        }
        height.isNullOrEmpty() -> {
            Toast.makeText(this,"Height is empty",Toast.LENGTH_LONG).show()
            return false
        }
        else -> return true
    }
    }

    private fun displayResult(bmi: Double) {
    val index = findViewById<TextView>(R.id.tvIndex)
    val resultDescription = findViewById<TextView>(R.id.tvResult)
    val info = findViewById<TextView>(R.id.tvInfo)

        index.text = bmi.toString()
        info.text = getString(R.string.info_text)


    var resultText = ""
    var color = 0

            when{
                bmi < 18.50 -> {
                    resultText = "Underweight"
                    color = R.color.underweight
                }
                bmi in 18.50..24.99 -> {
                    resultText = "Normal"
                    color = R.color.normal
                }
                bmi in 25.00 .. 29.99 -> {
                    resultText = "Overweight"
                    color = R.color.overweight
                }
                bmi > 29.99 -> {
                    resultText = "Obese"
                    color = R.color.obese
                }
        }
        resultDescription.setTextColor(ContextCompat.getColor(this,color))
        resultDescription.text = resultText
    }
}