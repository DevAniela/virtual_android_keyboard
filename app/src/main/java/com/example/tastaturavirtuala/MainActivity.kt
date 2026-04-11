package com.example.tastaturavirtuala

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Conectarea casetelor de text
        val editTextCurrent = findViewById<EditText>(R.id.textA)
        val textViewHistory = findViewById<TextView>(R.id.textViewHistory)

        // Activarea scroll-ului prin atingere pentru caseta cu istoricul
        textViewHistory.movementMethod = android.text.method.ScrollingMovementMethod()

        // Conectarea butoanelor de control
        val btnBack = findViewById<Button>(R.id.btnback)
        val btnOk = findViewById<Button>(R.id.btnOk)

        // Lista cu toate id-urile butoanelor hexa
        val butoaneHexaIds = listOf(
            R.id.btn0,
            R.id.btn1,
            R.id.btn2,
            R.id.btn3,
            R.id.btn4,
            R.id.btn5,
            R.id.btn6,
            R.id.btn7,
            R.id.btn8,
            R.id.btn9,
            R.id.btnA,
            R.id.btnB,
            R.id.btnC,
            R.id.btnD,
            R.id.btnE,
            R.id.btnF,
            R.id.btn0
        )

        // iteram prin lista de id-uri
        for (id in butoaneHexaIds) {
            val butonCurent = findViewById<Button>(id) // gasim butonul specific

            // ii spunem butonului ce sa faca atunci cand este apasat
            butonCurent.setOnClickListener {
                val textExistent = editTextCurrent.text.toString() // luam textul din caseta
                val literaButon =
                    butonCurent.text.toString() // luam litera de pe butonul pe care am apasat
                editTextCurrent.setText(textExistent + literaButon) // lipim litera noua la textul vechi si il afisam
            }
        }

        btnBack.setOnClickListener {
            val textCurent = editTextCurrent.text.toString()

            // verifcam caseta de text inainte de a sterge
            if (textCurent.isNotEmpty()) {
                val textScurtat = textCurent.dropLast(1) // se sterge ultima litera
                editTextCurrent.setText(textScurtat)
            }
        }

        btnOk.setOnClickListener {
            val textCurent = editTextCurrent.text.toString()

            // verificam daca utilizatorul a tastat ceva
            if (textCurent.isNotEmpty()) {
                val istoricExistent = textViewHistory.text.toString() // luam istoricul existent
                // adaugam numarul nou si un "\n" ca sa apara unele sub altele
                textViewHistory.text = istoricExistent + textCurent + "\n"
                // golim campul text de sus pentru o noua inserare
                editTextCurrent.setText("")
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}