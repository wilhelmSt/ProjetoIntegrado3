package com.example.futnow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class QuadraReserva : AppCompatActivity() {

    lateinit var option: Spinner
    lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quadra_reserva2)

        option = findViewById<Spinner>(R.id.SpinnerList)
        result = findViewById<TextView>(R.id.SpinnerText)

        val options = arrayOf("Lista 1", "Lista 2", "Lista 3")

        option.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                result.text = options[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                result.text = "Selecione uma lista"
            }
        }
    }
}