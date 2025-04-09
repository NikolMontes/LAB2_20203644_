package com.example.telememo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class VistaSegunda extends AppCompatActivity {
    GridLayout gridLayout;
    List<String> oracion;
    List<String> oracioningresada = new ArrayList<>();
    long inicio;
    int intentos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.segunda_vista);
        gridLayout=findViewById(R.id.grid_palabras);

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        String tema = getIntent().getStringExtra("tematica");
        oracion = fraseAleatoria(tema);

        List<String> palabras = new ArrayList<>(oracion);
        Collections.shuffle(palabras);
        for (String palabra : palabras) {
            Button btn = new Button(this);
            btn.setText(palabra);
            btn.setTextColor(Color.BLACK);
            btn.setOnClickListener(v -> manejarClick(btn));
            gridLayout.addView(btn);
        }
        inicio = SystemClock.elapsedRealtime();
    }

    private void manejarClick(Button btn) {
        String texto = btn.getText().toString();
        oracioningresada.add(texto);

        int idx = oracioningresada.size() - 1;
        if (idx >= oracion.size() || !oracion.get(idx).equals(texto)) {
            intentos++;
            oracioningresada.clear();
            Toast.makeText(this, "Incorrecto. Intentos: " + intentos + "/3", Toast.LENGTH_SHORT).show();
            if (intentos >= 3) finalizarJuego(false);
        } else if (oracioningresada.size() == oracion.size()) {
            finalizarJuego(true);
        }
    }

    private void finalizarJuego(boolean gano) {
        long fin = SystemClock.elapsedRealtime();
        long duracion = (fin - inicio) / 1000;

        Intent intent = new Intent(this, VistaResultado.class);
        intent.putExtra("gano", gano);
        intent.putExtra("tiempo", duracion);
        intent.putExtra("intentos", intentos);
        startActivity(intent);
        finish();
    }

    private List<String> fraseAleatoria(String tematica) {
        Map<String, String[]> oraciones = new HashMap<>();
        oraciones.put("software", new String[]{
                "Los fragments reutilizan partes de pantalla en distintas actividades de la app",
                "Los intents permiten acceder a apps como la cámara o WhatsApp directamente"
        });
        oraciones.put("ciberseguridad", new String[]{
                "Una VPN encripta tu conexión para navegar de forma anónima y segura",
                "El ataque DDoS satura servidores con tráfico falso y causa caídas masivas"
        });
        oraciones.put("opticas", new String[]{
                "La fibra óptica envía datos a gran velocidad evitando cualquier interferencia eléctrica",
                "Los amplificadores EDFA mejoran la señal óptica en redes de larga distancia"
        });
        String[] seleccion = oraciones.get(tematica);
        String seleccionRealizada = seleccion[new Random().nextInt(seleccion.length)];
        return Arrays.asList(seleccionRealizada.split(""));
    }


}