package es.ua.eps.sharedpreferences1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Base64
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etTexto: EditText
    private lateinit var seekBar: SeekBar
    private lateinit var tvTamanioActual: TextView
    private lateinit var tvValoresActuales: TextView
    private lateinit var btnAplicar: Button
    private lateinit var btnCerrar: Button
    private lateinit var btnJava: ImageButton
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializando componentes
        etTexto = findViewById(R.id.etTexto)
        seekBar = findViewById(R.id.seekBar)
        tvTamanioActual = findViewById(R.id.tvTamanioActual)
        tvValoresActuales = findViewById(R.id.tvValoresActuales)
        btnAplicar = findViewById(R.id.btnAplicar)
        btnCerrar = findViewById(R.id.btnCerrar)
        btnJava = findViewById(R.id.btnJava)

        // Configuración SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        // Inicializando valores predeterminados
        inicializarValores()

        seekBar.max = 49 // Configuramos el máximo del SeekBar en 49 (rango interno 0-49)

        // Listener del SeekBar
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val progresoReal = progress + 1 // Ajustar rango a 1-50
                tvTamanioActual.text = "Tamaño: ($progresoReal/50)"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Botón Aplicar
        btnAplicar.setOnClickListener {
            val texto = etTexto.text.toString()
            val tamanio = seekBar.progress + 1 // Calcular tamaño en base al progreso del SeekBar

            // Verificar que se ingrese texto antes de aplicar
            if (texto.isEmpty()) {
                Toast.makeText(this, "Por favor, introduce un texto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardar los valores en SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("texto", Base64.encodeToString(texto.toByteArray(), Base64.DEFAULT))
            editor.putInt("tamanio", tamanio)
            editor.apply()

            // Mostrar los valores aplicados en la actividad actual
            actualizarValoresActuales()

            // Iniciar DetailActivity con los datos ingresados
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("texto", texto)
            intent.putExtra("tamanio", tamanio)
            startActivity(intent)
        }

        // Botón para cerrar todas las actividades
        btnCerrar.setOnClickListener {
            finishAffinity()
        }

        // Botón para ir a la versión Java
        btnJava.setOnClickListener {
            val intent = Intent(this, MainActivityJava::class.java)
            startActivity(intent)
        }
    }

    private fun inicializarValores() {
        // Leer valores de SharedPreferences
        val textoGuardado = sharedPreferences.getString("texto", null)
        val tamanioPredeterminado = sharedPreferences.getInt("tamanio", 32)

        // Si no hay texto guardado, establecer valores predeterminados en SharedPreferences
        if (textoGuardado == null) {
            val editor = sharedPreferences.edit()
            editor.putString("texto", Base64.encodeToString("Hola Mundo!".toByteArray(), Base64.DEFAULT))
            editor.putInt("tamanio", tamanioPredeterminado)
            editor.apply()
        }

        // Configurar el texto en el EditText
        etTexto.setText(
            textoGuardado?.let { Base64.decode(it, Base64.DEFAULT).decodeToString() } ?: "Hola Mundo!"
        )

        // Ajustar el progreso del SeekBar al valor predeterminado
        seekBar.progress = tamanioPredeterminado - 1

        // Actualizar el TextView de tamaño actual
        tvTamanioActual.text = "Tamaño: ($tamanioPredeterminado/50)"

        // Actualizar los valores actuales en pantalla
        actualizarValoresActuales()
    }

    private fun actualizarValoresActuales() {
        // Obtener valores guardados
        val texto = sharedPreferences.getString("texto", null)
        val tamanio = sharedPreferences.getInt("tamanio", 32)

        val decodedTexto = texto?.let {
            Base64.decode(it, Base64.DEFAULT).decodeToString()
        } ?: ""

        val valoresTexto = """
            <b>Valores actuales de la aplicación:</b><br>
            <p>Texto: $decodedTexto</p>
            <p>Tamaño: $tamanio</p>
        """.trimIndent()

        // Actualizar los TextViews
        tvValoresActuales.text = HtmlCompat.fromHtml(valoresTexto, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
