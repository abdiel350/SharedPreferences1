package es.ua.eps.sharedpreferences1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

public class MainActivityJava extends AppCompatActivity {

    private EditText etTexto;
    private SeekBar seekBar;
    private TextView tvTamanioActual, tvValoresActuales;
    private Button btnAplicar, btnCerrar;
    private ImageButton btnKotlin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_java);

        // Inicializando componentes
        etTexto = findViewById(R.id.etTexto);
        seekBar = findViewById(R.id.seekBar);
        tvTamanioActual = findViewById(R.id.tvTamanioActual);
        tvValoresActuales = findViewById(R.id.tvValoresActuales);
        btnAplicar = findViewById(R.id.btnAplicar);
        btnCerrar = findViewById(R.id.btnCerrar);
        btnKotlin = findViewById(R.id.btnkotlin);

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Configurar valores iniciales
        inicializarValores();

        // Configurar máximo del SeekBar
        seekBar.setMax(49);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTamanioActual.setText("Tamaño: (" + (progress + 1) + "/50)");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Configurar botón Aplicar
        btnAplicar.setOnClickListener(v -> {
            String texto = etTexto.getText().toString();

            // Determinar el tamaño a aplicar
            int tamanio;
            if (seekBar.getProgress() == 0) {
                // Si el progreso es 0, usar el valor predeterminado de SharedPreferences
                tamanio = sharedPreferences.getInt("tamanio", 32);
            } else {
                // Si el progreso no es 0, calcular el tamaño desde el SeekBar
                tamanio = seekBar.getProgress() + 1;
            }

            // Verificar que el texto no esté vacío
            if (texto.isEmpty()) {
                Toast.makeText(this, "Por favor, introduce un texto", Toast.LENGTH_SHORT).show();
                return;
            }

            // Guardar los valores en SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("texto", Base64.encodeToString(texto.getBytes(), Base64.DEFAULT));
            editor.putInt("tamanio", tamanio);
            editor.apply();

            // Actualizar los valores actuales
            actualizarValoresActuales();

            // Navegar a DetailActivity
            Intent intent = new Intent(this, DetailActivityJava.class);
            startActivity(intent);
        });

        // Configurar botón Cerrar actividad actual
        btnCerrar.setOnClickListener(v -> finishAffinity());

        // Configurar botón para ir a Kotlin
        btnKotlin.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void inicializarValores() {
        // Obtener los valores guardados en SharedPreferences
        String texto = sharedPreferences.getString("texto", "");
        int tamanio = sharedPreferences.getInt("tamanio", 32); // Valor predeterminado es 32

        // Configurar el texto predeterminado
        if (texto.isEmpty()) {
            etTexto.setText("Hola Mundo!");
        } else {
            String decodedTexto = new String(Base64.decode(texto, Base64.DEFAULT));
            etTexto.setText(decodedTexto);
        }

        // Configurar el SeekBar
        seekBar.setProgress(tamanio - 1); // Ajustar el progreso al rango del SeekBar (0-49)
        tvTamanioActual.setText("Tamaño: (" + tamanio + "/50)");

        // Actualizar los valores actuales en pantalla
        actualizarValoresActuales();
    }

    private void actualizarValoresActuales() {
        String texto = sharedPreferences.getString("texto", "");
        int tamanio = sharedPreferences.getInt("tamanio", 32);

        String decodedTexto = "";
        if (texto != null && !texto.isEmpty()) {
            decodedTexto = new String(Base64.decode(texto, Base64.DEFAULT));
        }

        String valoresTexto = "<b>Valores actuales de la aplicación:</b><br>"
                + "<p>Texto: " + decodedTexto + "</p>"
                + "<p style=\"margin-top: 5dp;\">Tamaño: " + tamanio + "</p>";

        tvValoresActuales.setText(HtmlCompat.fromHtml(valoresTexto, HtmlCompat.FROM_HTML_MODE_LEGACY));
    }
}
