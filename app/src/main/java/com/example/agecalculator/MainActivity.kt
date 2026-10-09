package com.example.agecalculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.DatePickerDialog
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etDob = findViewById<EditText>(R.id.etDob)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        etDob.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "${String.format(Locale.US, "%02d", selectedDayOfMonth)}/${String.format(Locale.US, "%02d", selectedMonth + 1)}/$selectedYear"
                etDob.setText(selectedDate)
            }, year, month, day)

            dpd.show()
        }

        btnCalculate.setOnClickListener {
            if (etDob.text.toString().isEmpty()) {
                tvResult.text = "Please select a date of birth"
            } else {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                val theDate = sdf.parse(etDob.text.toString())
                
                theDate?.let {
                    val dobCalendar = Calendar.getInstance()
                    dobCalendar.time = it
                    val birthYear = dobCalendar.get(Calendar.YEAR)
                    val birthMonth = dobCalendar.get(Calendar.MONTH)
                    val birthDay = dobCalendar.get(Calendar.DAY_OF_MONTH)
                    
                    val currentCalendar = Calendar.getInstance()
                    val currentYear = currentCalendar.get(Calendar.YEAR)
                    val currentMonth = currentCalendar.get(Calendar.MONTH)
                    val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
                    
                    var age = currentYear - birthYear
                    
                    if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
                        age--
                    }
                    
                    tvResult.text = "Your Age: $age years"
                }
            }
        }
    }
}