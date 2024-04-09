package com.example.mastermind.activites;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mastermind.R;

public class Jouer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jouer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.llfondJouer), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /*
        // Définir les variables pour le nombre de lignes et de colonnes
        int numRows = 10;
        int numColumns = 4;

        // Tableau des couleurs au format RGB
        int[] colors = {
                0xffff0000, // Rouge
                0xff00ff00, // Vert
                0xff0000ff, // Bleu
                0xffffff00, // Jaune
                0xffff00ff, // Magenta
                0xffffa500, // Orange
                0xff000000, // Noir
                0xffffffff  // Blanc
        };

        // Trouver le GridLayout dans votre layout
        GridLayout grilleJeu = findViewById(R.id.glJeu);

        // Boucler à travers les lignes et les colonnes pour créer des boutons dynamiquement
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                Button bouton = new Button(this);
                bouton.setLayoutParams(new GridLayout.LayoutParams(
                        GridLayout.spec(i), GridLayout.spec(j)));

                // Attribuer aux boutons toutes les couleurs
                int colorIndex = i * numColumns + j;
                if (colorIndex < colors.length) {
                    bouton.setBackgroundColor(colors[colorIndex]);
                } else {
                    // Si toutes les couleurs ont été utilisées, définir le fond en gris
                    bouton.setBackgroundColor(0xff999999);
                }

                // Définir la taille et les marges du bouton
                GridLayout.LayoutParams params = (GridLayout.LayoutParams) bouton.getLayoutParams();
                params.width = 50;
                params.height = 50;
                params.setMargins(10, 5, 10, 5);

                grilleJeu.addView(bouton);
            }
        }

        */
    }
}