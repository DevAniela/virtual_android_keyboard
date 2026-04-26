package com.example.tastaturavirtuala

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity2 : AppCompatActivity() {

    private var istoricOperatii = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. SCOATEM LISTA DIN INTENT (Ecranul 1)
        // daca nu gasim nimic (e null), cream o lista goala de rezerva
        val istoricOperatii = intent.getStringArrayListExtra("ISTORIC") ?: ArrayList<String>()

        val listView = findViewById<android.widget.ListView>(R.id.ListViewHistory)

        // 2. trimitem lista catre adaptorul vizual
        val adapter = android.widget.ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            istoricOperatii
        )
        listView.adapter = adapter

        // 3. logica de click pe un element din lista
        listView.setOnItemClickListener { _, _, position, _ ->
            val ecuatieSelectata = istoricOperatii[position]
            val doarRezultatul = ecuatieSelectata.substringAfter("=").trim()

            // pregatire pt intoarcere
            val intentInapoi = android.content.Intent(this, MainActivity::class.java)

            // punem valoarea pe care o vrea utilizatorul
            intentInapoi.putExtra("VALOARE_SELECTATA", doarRezultatul)

            // ducem LISTA INAPOI, ca MainActivity sa nu o uite
            intentInapoi.putStringArrayListExtra("ISTORIC", istoricOperatii)

            intentInapoi.flags = android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intentInapoi)
        }
    }

    // aceasta functie construieste meniul de sus
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        // aduce fișierul XML (menu_main) pe ecran
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // aceasta functie trateaza click-urile pe butoanele din bara de sus
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {

        // 1. Butonul CALCUL (intoarcere simpla fara valoare selectata)
        if (item.itemId == R.id.action_calcul) {
            val intentInapoi = android.content.Intent(this, MainActivity::class.java)
            intentInapoi.putStringArrayListExtra("ISTORIC", istoricOperatii)
            intentInapoi.flags = android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intentInapoi)
            return true
        }

        // 2. Butonul EMAIL (trimitere istoric)
        if (item.itemId == R.id.action_email) {
            val intentEmail = android.content.Intent(android.content.Intent.ACTION_SEND)
            intentEmail.type = "message/rfc822"
            intentEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, "Tema 2 Android - Istoric calcule")

            val istoricCaText = istoricOperatii.joinToString(separator = "\n")
            intentEmail.putExtra(android.content.Intent.EXTRA_TEXT, "istoricul meu:\n\n$istoricCaText")

            try {
                startActivity(android.content.Intent.createChooser(intentEmail, "Alege aplicația de mail..."))
            } catch (e: Exception) {
                // eroare daca nu exista aplicatie de email
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}