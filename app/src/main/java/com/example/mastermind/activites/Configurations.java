package com.example.mastermind.activites;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mastermind.R;

public class Configurations extends AppCompatActivity implements View.OnClickListener {
    private Button annuler;
    private Button confirmer;
    GridLayout grilleCode;
    Integer colonnesCode = 5;
    Integer colonnesCouleurs = 7;
    Integer colonnesTentatives = 5;

    private int longueurCode;
    private int nbCouleurs;
    private int nbMaxDeTentative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configurations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.llfondConfigurations), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        annuler = findViewById(R.id.bAnnulerConfigurations);
        annuler.setOnClickListener(this);

        confirmer = findViewById(R.id.bConfirmerConfigurations);
        confirmer.setOnClickListener(this);


        //recoi
        Intent intent = getIntent();
        if (intent != null) {
            longueurCode = intent.getIntExtra("longueurCode", 4);
            nbCouleurs = intent.getIntExtra("nbCouleurs", 8);
            nbMaxDeTentative = intent.getIntExtra("nbMaxDeTentative", 10);
        }

        remplirGrilles(longueurCode, nbCouleurs, nbMaxDeTentative);

    }

    @Override
    public void onClick(View v) {
        if (v == annuler) {
            setResult(RESULT_CANCELED);
            finish();
        } else if (v == confirmer) {
            // Sauvegarder les données
            //TODO

            Intent resultatIntent = new Intent();
            resultatIntent.putExtra("longueurCode", longueurCode);
            resultatIntent.putExtra("nbCouleurs", nbCouleurs);
            resultatIntent.putExtra("nbMaxDeTentative", nbMaxDeTentative);
            setResult(RESULT_OK, resultatIntent);

            // Fermer
            finish();
        } else {
            // Récupérer le GridLayout parent du bouton cliqué
            GridLayout parentLayout = (GridLayout) v.getParent();

            // Parcourir tous les boutons dans le GridLayout parent
            for (int i = 0; i < parentLayout.getChildCount(); i++) {
                Button bouton = (Button) parentLayout.getChildAt(i);
                Drawable drawable;

                // Vérifier si le bouton est celui cliqué ou non
                if (bouton == v) {

                    // Définir l'arrière-plan du bouton cliqué en vert
                    drawable = getResources().getDrawable(R.drawable.bouton_rectangle_choix_vert);
                    // Associer la valeur du bouton aux variables appropriées
                    String buttonText = bouton.getText().toString();
                    if (parentLayout.getId() == R.id.llCode) {
                        longueurCode = Integer.parseInt(buttonText);
                    } else if (parentLayout.getId() == R.id.llCouleurs) {
                        nbCouleurs = Integer.parseInt(buttonText);
                    } else if (parentLayout.getId() == R.id.llTentatives) {
                        nbMaxDeTentative = Integer.parseInt(buttonText);
                    }
                } else {
                    // Définir l'arrière-plan des autres boutons en gris
                    drawable = getResources().getDrawable(R.drawable.bouton_rectangle_choix);
                }
                bouton.setBackground(drawable);
            }
        }
    }

    private void remplirGrilles(int longueurCode, int nbCouleurs, int nbMaxDeTentative) {
        // Personnaliser la grille des codes
        grilleCode = findViewById(R.id.llCode);
        grilleCode.setColumnCount(colonnesCode);

        // Remplir la grille contenant la longueur du code
        for (int i = 0; i < colonnesCode; i++) {
            personnaliserBoutonDefaut(grilleCode, "" + (i + 2), longueurCode, nbCouleurs, nbMaxDeTentative);
        }

        // Personnaliser la grille des couleurs
        GridLayout grilleCouleurs = findViewById(R.id.llCouleurs);
        grilleCouleurs.setColumnCount(colonnesCouleurs);

        // Remplir la grille contenant le nombre de couleurs
        for (int i = 0; i < colonnesCouleurs; i++) {
            personnaliserBoutonDefaut(grilleCouleurs, "" + (i + 2), longueurCode, nbCouleurs, nbMaxDeTentative);
        }

        // Personnaliser la grille des tentatives
        GridLayout grilleTentatives = findViewById(R.id.llTentatives);
        grilleTentatives.setColumnCount(colonnesTentatives);

        // Remplir la grille contenant le nombre maximum de tentatives
        for (int i = 0; i < colonnesTentatives; i++) {
            personnaliserBoutonDefaut(grilleTentatives, "" + (i + 8), longueurCode, nbCouleurs, nbMaxDeTentative);
        }
    }

    private void personnaliserBoutonDefaut(GridLayout gridLayout, String buttonText, int longueurCode, int nbCouleurs, int nbMaxDeTentative) {
        Button bouton = new Button(this); // Créer un bouton
        bouton.setOnClickListener(this); // Écouteur de click
        // Personnaliser les boutons
        bouton.setText(buttonText); // Longueur du code
        bouton.setTextSize(20);
        bouton.setTextColor(Color.WHITE);
        Drawable drawable;


        // Paramètres par défaut    replace hard code with String.valueOf(longueurCode) to have it adapt to the current value
        if (gridLayout.getId() == R.id.llCode && buttonText.equals(String.valueOf(longueurCode))) {
            drawable = getResources().getDrawable(R.drawable.bouton_rectangle_choix_vert);
        } else if (gridLayout.getId() == R.id.llCouleurs && buttonText.equals(String.valueOf(nbCouleurs))) {
            drawable = getResources().getDrawable(R.drawable.bouton_rectangle_choix_vert);
        } else if (gridLayout.getId() == R.id.llTentatives && buttonText.equals(String.valueOf(nbMaxDeTentative))) {
            drawable = getResources().getDrawable(R.drawable.bouton_rectangle_choix_vert);
        } else {
            drawable = getResources().getDrawable(R.drawable.bouton_rectangle_choix);
        }

        bouton.setBackground(drawable);

        // Créer un spécifique pour la position du bouton dans la grille
        GridLayout.Spec rowSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
        GridLayout.Spec colSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);

        // Calculer la largeur de l'écran et définir la taille des boutons
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int buttonWidth = (screenWidth - (40 * (colonnesCode + 1))) / colonnesCode;

        params.width = buttonWidth;
        params.height = buttonWidth;
        params.setMargins(20, 20, 20, 20); // Marges

        bouton.setLayoutParams(params); // Appliquer les paramètres de la marge

        gridLayout.addView(bouton); // Ajouter le bouton dans la grille
    }
}