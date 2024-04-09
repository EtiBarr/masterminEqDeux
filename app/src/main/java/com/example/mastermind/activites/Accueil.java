package com.example.mastermind.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Patterns;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mastermind.R;

public class Accueil extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CONFIG = 1;

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

    private int longueurCode = 4; // valeur par default
    private int nbCouleurs = 8; // valeur par default
    private int nbMaxDeTentative = 10; // valeur par default
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CONFIG) {
            if (resultCode == RESULT_OK) {

                longueurCode = data.getIntExtra("longueurCodeChoisi", 4);
                nbCouleurs = data.getIntExtra("nbCouleurs", 8);
                nbMaxDeTentative = data.getIntExtra("nbMaxDeTentative", 10);

                // Display the values of the variables using Toast for testing ***********************
                String message = "Longueur du code: " + longueurCode + "\n" +
                                "Nombre de couleurs: " + nbCouleurs + "\n" +
                                "Nombre maximal de tentatives: " + nbMaxDeTentative;
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == jouer) {

            if (!courriel.getText().toString().isEmpty()) {
                if(Patterns.EMAIL_ADDRESS.matcher(courriel.getText().toString().trim()).matches()) {
                    Intent versJeu = new Intent(this, Jouer.class);
                    versJeu.putExtra("courriel", courriel.getText().toString());
                    versJeu.putExtra("longueurCode", longueurCode);
                    versJeu.putExtra("nbCouleurs", nbCouleurs);
                    versJeu.putExtra("nbMaxDeTentative", nbMaxDeTentative);
                    startActivity(versJeu);
                }else{
                    Toast.makeText(this, "Le format du couriel doit etre valid", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Il faut include un courriel", Toast.LENGTH_SHORT).show();
            }

        } else if (v == configurations) {

            Intent versConfigurations = new Intent(this, Configurations.class);
            versConfigurations.putExtra("longueurCode", longueurCode); // envoi la longueur courant
            versConfigurations.putExtra("nbCouleurs", nbCouleurs); // envoi la nombre de coulerus courant
            versConfigurations.putExtra("nbMaxDeTentative", nbMaxDeTentative); // envoi le nombre maximum de tentatives courant
            startActivityForResult(versConfigurations, REQUEST_CONFIG);

        } else if (v == historique) {
            Intent versHistorique = new Intent(this, Historique.class);
            startActivity(versHistorique);
        }
    }


}