package es.ua.eps.sharedpreferences1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivityJava extends AppCompatActivity {

    private TextView tvResultado;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_java);

        tvResultado = findViewById(R.id.tvResultado);
        btnVolver = findViewById(R.id.btnVolver);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        String texto = sharedPreferences.getString("texto", "Hola Mundo!");
        int tamanio = sharedPreferences.getInt("tamanio", 32);

        if (texto != null) {
            String decodedTexto = new String(Base64.decode(texto, Base64.DEFAULT));
            tvResultado.setText(decodedTexto);
        }
        tvResultado.setTextSize(tamanio);

        btnVolver.setOnClickListener(v -> finish());
    }
}
