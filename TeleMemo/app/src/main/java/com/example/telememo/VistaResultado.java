package com.example.telememo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VistaResultado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_vista);
        boolean gano = getIntent().getBooleanExtra("gano", false);
        long tiempo = getIntent().getLongExtra("tiempo", 0);
        int intentos = getIntent().getIntExtra("intentos", 0);

        TextView resultado = findViewById(R.id.textResult);
        TextView info = findViewById(R.id.textNumIntento);
        Button nuevo = findViewById(R.id.btn_new_game);

        resultado.setText(gano ? "¡Ganaste!" : "Perdiste");
        info.setText("Tiempo: " + tiempo + "s\n" + (gano ? ("Intentos: " + intentos) : ""));

        nuevo.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}