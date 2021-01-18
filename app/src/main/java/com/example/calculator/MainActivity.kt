package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), InputFragment.MainListener {
    lateinit var textResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        textResult = findViewById(R.id.text_number)
    }

    override fun onResultUpdate(string: String) {
        textResult.text = string
    }
}
