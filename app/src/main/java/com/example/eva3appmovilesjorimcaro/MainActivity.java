package com.example.eva3appmovilesjorimcaro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botón para ir a GestionarRemediosActivity
        Button btnGestionar = findViewById(R.id.btn_gestionar);
        btnGestionar.setOnClickListener(view ->
                startActivity(new Intent(this, GestionarRemediosActivity.class))
        );
    }
}