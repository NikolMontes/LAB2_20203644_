package com.example.telememo;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.telememo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
        //    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        //    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        //    return insets;
        //});
        findViewById(R.id.btnSoftware).setOnClickListener(v -> juegoinit("software"));
        findViewById(R.id.btn_ciber).setOnClickListener(v -> juegoinit("ciberseguridad"));
        findViewById(R.id.btn_opticas).setOnClickListener(v -> juegoinit("opticas"));
    }

    private void juegoinit(String tematica){
        Intent intent = new Intent(this, VistaSegunda.class);
        intent.putExtra("tematica", tematica);
        startActivity(intent);
    }
}