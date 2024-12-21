package es.ua.eps.sharedpreferences1

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var tvResultado: TextView
    private lateinit var btnVolver: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvResultado = findViewById(R.id.tvResultado)
        btnVolver = findViewById(R.id.btnVolver)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // Decodificar valores almacenados
        val texto = sharedPreferences.getString("texto", "Hola Mundo!")
        val tamanio = sharedPreferences.getInt("tamanio", 32)

        tvResultado.text = texto?.let { Base64.decode(it, Base64.DEFAULT).decodeToString() }
        tvResultado.textSize = tamanio.toFloat()

        // Botón para regresar
        btnVolver.setOnClickListener { finish() }
    }
}