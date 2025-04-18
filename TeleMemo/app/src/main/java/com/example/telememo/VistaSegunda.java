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
/*Parte del codigo ha sido parcialmente obtenido usando IA (chatGPT), especialmente para facilitar la estructura de los métodos
y para la separación de las oraciones en palabras, así como organizarlas en las tarjetas aleatoriamente*/

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
        Collections.shuffle(oracion);

        for (int i = 0; i < 12; i++) {
            Button btn = new Button(this);
            btn.setText("");
            gridLayout.addView(btn);
        }
        String oracionencontrada = String.join(" ", oracion);

        findViewById(R.id.btn_jugar).setOnClickListener(v -> {
            Intent intent = new Intent(this, VistaResultado.class);
            intent.putExtra("tematica", tema);
            intent.putExtra("oracion", oracionencontrada);
            startActivity(intent);
        });

    }

    private void manejarClick(Button btn) {
        String texto = (String)btn.getTag();
        btn.setText(texto);
        oracioningresada.add(texto);

        int idx = oracioningresada.size() - 1;
        if (idx >= oracion.size() || !oracion.get(idx).equals(texto)) {
            intentos++;
            //oracioningresada.clear();
            Toast.makeText(this, "Incorrecto. Intentos: " + intentos + "/3", Toast.LENGTH_SHORT).show();
            new android.os.Handler().postDelayed(() -> {
                for (int i=0;i< gridLayout.getChildCount();i++){
                    Button b = (Button) gridLayout.getChildAt(i);
                    if (!oracioningresada.contains(b.getTag())) {
                        b.setText("");
                    }
                }
                oracioningresada.clear();
            }, 2000);
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
        //String seleccionRealizada  = seleccion[1];
        String seleccionRealizada = seleccion[new Random().nextInt(seleccion.length)];
        return Arrays.asList(seleccionRealizada.split(" "));
    }


}