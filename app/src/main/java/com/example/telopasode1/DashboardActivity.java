package com.example.telopasode1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DashboardActivity extends AppCompatActivity {

    CardView cardInmuebles, cardEmpleos, cardAutomotores, cardOtros, cardAvisos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Inicializar los CardViews
        cardInmuebles = findViewById(R.id.cardInmuebles);
        cardEmpleos = findViewById(R.id.cardEmpleos);
        cardAutomotores = findViewById(R.id.cardAutomotores);
        cardOtros = findViewById(R.id.cardOtros);
        cardAvisos = findViewById(R.id.cardAvisos);

        // Configurar listeners para cada categoría
        cardInmuebles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirListaAnuncios("inmuebles");
            }
        });

        cardEmpleos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirListaAnuncios("empleos");
            }
        });

        cardAutomotores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirListaAnuncios("automotores");
            }
        });

        cardOtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirListaAnuncios("otros");
            }
        });

        cardAvisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirListaAnuncios("avisos");
            }
        });
    }


    private void abrirListaAnuncios(String categoria) {
        if (categoria == null || categoria.isEmpty()) {
            Toast.makeText(this, "Categoría no disponible", Toast.LENGTH_SHORT).show();
            return; // Detener la ejecución si la categoría es inválida
        }

        Intent intent = new Intent(getApplicationContext(), AdListActivity.class);
        intent.putExtra("categoria", categoria);
        startActivity(intent);
    }


}
