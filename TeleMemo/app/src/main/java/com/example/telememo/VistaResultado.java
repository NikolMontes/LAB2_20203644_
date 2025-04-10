package com.example.telememo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class VistaResultado extends AppCompatActivity {
    GridLayout gridLayout;
    TextView mensaje;
    TextView info;
    List<String> oracion;
    List<String> oracionIngresada = new ArrayList<>();
    long inicio;
    int intentos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_vista);

        gridLayout = findViewById(R.id.grid_palabras);
        mensaje = findViewById(R.id.textResult);
        info = findViewById(R.id.textNumIntento);

        Toolbar toolbar = findViewById(R.id.toolbar_juego);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        String oracionTexto = getIntent().getStringExtra("oracion");
        oracion = Arrays.asList(oracionTexto.split(" "));

        List<String> palabras = new ArrayList<>(oracion);
        Collections.shuffle(palabras);

        for (String palabra : palabras) {
            Button btn = new Button(this);
            btn.setText("");
            btn.setTag(palabra);
            btn.setTextColor(Color.BLACK);
            btn.setOnClickListener(v -> manejarClick(btn));
            gridLayout.addView(btn);
        }

        inicio = SystemClock.elapsedRealtime();
        mensaje.setText("");
        info.setText("");

    }
    private void manejarClick(Button btn) {
        String palabra = (String) btn.getTag();
        btn.setText(palabra);
        oracionIngresada.add(palabra);

        int idx = oracionIngresada.size() - 1;
        if (idx >= oracion.size() || !oracion.get(idx).equals(palabra)) {
            intentos++;
            int restantes = 3 - intentos;
            info.setText("Te quedan " + restantes + " intentos");
            //Toast.makeText(this, "Incorrecto. Te quedan " + (3 - intentos) + " intentos", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> {
                for (int i = 0; i < gridLayout.getChildCount(); i++) {
                    Button b = (Button) gridLayout.getChildAt(i);
                    b.setText("");
                }
                oracionIngresada.clear();
                if (intentos >= 3) finalizarJuego(false);
            }, 1000);

            if (intentos >= 3) finalizarJuego(false);
        } else if (oracionIngresada.size() == oracion.size()) {
            finalizarJuego(true);
        }
    }

    private void finalizarJuego(boolean gano) {
        long duracion = (SystemClock.elapsedRealtime() - inicio) / 1000;
        mensaje.setVisibility(View.VISIBLE);
        mensaje.setText(gano ? "¡Ganaste! / Terminó en " + duracion + "s" : "Perdiste / Terminó en " + duracion + "s");
        info.setVisibility(View.INVISIBLE);
        Button btn = findViewById(R.id.btn_new_game);
        btn.setText("Nuevo Juego");
        btn.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });
    }

}