package com.example.mastermind.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mastermind.R;

public class Accueil extends AppCompatActivity implements View.OnClickListener {

    // Déclaration des éléments
    private TextView jouer;
    private TextView configurations;
    private TextView historique;
    private EditText courriel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accueil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.llfondAccueil), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        jouer = findViewById(R.id.tvJouerAccueil);
        jouer.setOnClickListener(this);

        configurations = findViewById(R.id.tvConfigurationsAccueil);
        configurations.setOnClickListener(this);

        historique = findViewById(R.id.tvHistoriqueAccueil);
        historique.setOnClickListener(this);

        courriel = findViewById(R.id.etCourrielAccueil);
    }

    @Override
    public void onClick(View v) {
        if (v == jouer) {

            if (courriel.getText().toString() != "") {
                Intent versJeu = new Intent(this, Jouer.class);
                versJeu.putExtra("courriel", courriel.getText().toString());
                startActivity(versJeu);
            }

            else {
            }

        } else if (v == configurations) {
            Intent versConfigurations = new Intent(this, Configurations.class);
            startActivity(versConfigurations);
        } else if (v == historique) {
            Intent versHistorique = new Intent(this, Historique.class);
            startActivity(versHistorique);
        }
    }
}