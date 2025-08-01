package com.example.bmicalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {

    private lateinit var weight: EditText
    private lateinit var height: EditText
    private lateinit var display: TextView
    private lateinit var clearButton: Button
    private lateinit var calculateButton: Button
    private lateinit var weightSlider: Slider
    private lateinit var heightSlider: Slider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weight = findViewById(R.id.weight)
        height = findViewById(R.id.height)
        display = findViewById(R.id.display)
        clearButton = findViewById(R.id.clr_btn)
        calculateButton = findViewById(R.id.calculate_btn)
        weightSlider = findViewById(R.id.weight_slider)
        heightSlider = findViewById(R.id.height_slider)

        weightSlider.addOnChangeListener { slider, value, fromUser ->
            weight.setText(value.toInt().toString())
        }

        heightSlider.addOnChangeListener { slider, value, fromUser ->
            height.setText(value.toInt().toString())
        }

        calculateButton.setOnClickListener {
            val weightText = weight.text.toString()
            val heightText = height.text.toString()

            if (weightText.isBlank() || heightText.isBlank()) {
                showError("Please fill in all fields")
            } else {
                val weightValue = weightText.toDoubleOrNull()
                val heightValue = heightText.toDoubleOrNull()

                if (weightValue != null && heightValue != null) {
                    val meter = heightValue / 100
                    val bmi = weightValue / (meter * meter)
                    val result = String.format("%.2f", bmi)

                    val bmiMessage: String
                    val backgroundResource: Int

                    if (bmi >= 40) {
                        bmiMessage = "Obesity class 3"
                        backgroundResource = R.drawable.result_border_red
                    } else if (bmi >= 35) {
                        bmiMessage = "Obesity class 2"
                        backgroundResource = R.drawable.result_border_red
                    } else if (bmi >= 30) {
                        bmiMessage = "Obesity class 1"
                        backgroundResource = R.drawable.result_border_red
                    } else if (bmi >= 25) {
                        bmiMessage = "You are Overweight"
                        backgroundResource = R.drawable.result_border_red
                    } else if (bmi >= 18) {
                        bmiMessage = "You are Healthy"
                        backgroundResource = R.drawable.result_border_green
                    } else {
                        bmiMessage = "You are Underweight"
                        backgroundResource = R.drawable.result_border_yellow
                    }

                    display.text = "$result\n$bmiMessage"
                    display.setBackgroundResource(backgroundResource)
                    Toast.makeText(this, "Your BMI is $result: $bmiMessage", Toast.LENGTH_LONG)
                        .show()
                } else {
                    showError("Please enter valid information")
                }
            }
        }

        clearButton.setOnClickListener {
            weight.setText("")
            height.setText("")
            display.setText("Your BMI: --")
            display.setBackgroundResource(R.drawable.display_background)
            weightSlider.value = 70f
            heightSlider.value = 170f
        }
    }

    private fun showError(message: String) {
        display.text = message
        display.setBackgroundResource(R.drawable.result_border_red)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
