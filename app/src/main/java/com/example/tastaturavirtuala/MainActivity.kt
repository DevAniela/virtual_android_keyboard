package com.example.tastaturavirtuala

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

        // conectarea casetelor de text (acum avem două)
        val textA = findViewById<EditText>(R.id.textA)
        val textC = findViewById<TextView>(R.id.textC)

        // textul din A e setat la zero la inceput
        textA.setText("0")

        // conectarea butoanelor de control
        val btnBack = findViewById<Button>(R.id.btnback)
        val btn10_16 = findViewById<Button>(R.id.btn10_16)

        // conectarea butoanelor de operatii
        val btnPlus = findViewById<Button>(R.id.btnPlus)
        val btnMinus = findViewById<Button>(R.id.btnMinus)
        val btnInmultire = findViewById<Button>(R.id.btnInmultire)
        val btnEgal = findViewById<Button>(R.id.btnEgal)

        // se cere ca butonul egal sa fie initial dezactivat
        btnEgal.isEnabled = false

        // cream o functie lambda pt a nu scrie acelasi cod de mai multe ori
        val aplicaOperatie = {
            operatie: String -> val textCurent = textC.text.toString()

            // copiem nr din textC în textA și adaugam operatia
            textA.setText("$textCurent $operatie")

            // resetam textC la 0 pt a putea tasta urmatorul nr
            textC.setText("0")

            // activam butonul egal
            btnEgal.isEnabled = true
        }

        // legam butoanele de functia de mai sus
        btnPlus.setOnClickListener { aplicaOperatie("+") }
        btnMinus.setOnClickListener { aplicaOperatie("-") }
        btnInmultire.setOnClickListener { aplicaOperatie("*") }
        // calculele pt butonul egal
        btnEgal.setOnClickListener {
            val textAVal = textA.text.toString()
            val textCVal = textC.text.toString()

            // verificam daca exista o operatie salvata in textA (exista spatiu)
            if(textAVal.isNotEmpty() && textAVal.contains(" ")) {
                // despartim textul din A in doua folosind spatiul ca delimitator
                val parti = textAVal.split(" ")
                val nr1Str = parti[0]
                val operatie = parti[1]
                val nr2Str = textCVal

                try {
                    // convertim textele in numere
                    val nr1 = nr1Str.toLong()
                    val nr2 = nr2Str.toLong()
                    var rezultat = 0L // long

                    // efectuam calculul in functie de semn
                    when (operatie) {
                        "+" -> rezultat = nr1 + nr2
                        "-" -> rezultat = nr1 - nr2
                        "*" -> rezultat = nr1 * nr2
                    }

                    // afisam rezultatul
                    textA.setText("$rezultat =")

                    // resetam textC la 0
                    textC.setText("0")

                    // TODO: adaugam cod pt istoric
                } catch (e: Exception) {
                    textA.setText("Eroare")
                }
            }
            btnEgal.isEnabled = false
        }

        // lista cu toate id-urile butoanelor hexa
        val butoaneHexaIds = listOf(
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
                val textExistent = textC.text.toString() // luam textul din caseta textC
                val literaButon = butonCurent.text.toString() // luam litera de pe butonul pe care am apasat

                // daca nu am avea 0 la inceput
                if (textExistent == "0")
                {
                    textC.setText(literaButon)
                } else {
                    textC.setText(textExistent + literaButon) // lipim litera noua la textul vechi si il afisam
                }
            }
        }

        btnBack.setOnClickListener {
            val textCurent = textC.text.toString()

            // verificam caseta de text inainte de a sterge
            if (textCurent.isNotEmpty()) {
                val textScurtat = textCurent.dropLast(1) // se șterge ultima litera
                textC.setText(textScurtat)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    // FUNCTIILE PENTRU MENIU

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        // cand apasam pe istoric in primul ecran, mergem la Activity2
        if(item.itemId == R.id.action_istoric) {
            val intent = android.content.Intent(this, Activity2::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}