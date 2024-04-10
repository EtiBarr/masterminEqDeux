package com.example.mastermind.activites;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mastermind.R;
import com.example.mastermind.dao.MastermindDao;
import com.example.mastermind.modele.Mastermind;
import com.example.mastermind.presentateur.PresenteurMastermind;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class Jouer extends AppCompatActivity {

    private String courriel;
    private int longueurCode;
    private int nbCouleurs;
    private int nbMaxDeTentative;
    private GridLayout grille;
    private LinearLayout lvCodeSecret;
    private LinearLayout lvCouleursDisponibles;

    private LinearLayout lvTentatives;


    private PresenteurMastermind presenteurMastermind;

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


        Intent intent = getIntent();
        if (intent != null) {
            courriel = intent.getStringExtra("courriel");
            longueurCode = intent.getIntExtra("longueurCode", 4); // valeur par defaut si le il y a un probleme avec le intent
            nbCouleurs = intent.getIntExtra("nbCouleurs", 8);
            nbMaxDeTentative = intent.getIntExtra("nbMaxDeTentative", 10);
            // Displaying a toast message with all the received variables
            String message = "Email: " + courriel + "\n"
                    + "Longueur du Code: " + longueurCode + "\n"
                    + "Nombre de Couleurs: " + nbCouleurs + "\n"
                    + "Nombre Max de Tentative: " + nbMaxDeTentative;
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
        }

        System.out.println("nb couleurs = " + nbCouleurs);
        grille = findViewById(R.id.glJeu);
        lvCouleursDisponibles = findViewById(R.id.lvCouleursDisponibles);
        lvCodeSecret = findViewById(R.id.lvCodeSecretJouer);
        lvTentatives = findViewById(R.id.lvTentative);


    /*    runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    afficherCouleursDisponible();
                    afficherCodeSecret();
                    afficherGrille();
                    afficherTentative(4);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });*/
        presenteurMastermind = new PresenteurMastermind(this);
        presenteurMastermind.initializer(nbCouleurs);





    }
/*
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            try {
                afficherCouleursDisponible();
                afficherCodeSecret();
                afficherGrille();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    });
    */

    public void afficherCouleursDisponible() throws JSONException, IOException {

        ArrayList<String> couleurs = presenteurMastermind.obtenirCouleurs();

        // Afficher les couleurs
        for (int i = nbCouleurs-1; i >= 0; i--) {
            Button boutonCouleur = new Button(Jouer.this);
            boutonCouleur.setBackgroundResource(R.drawable.bouton_rectangle);

            int couleurInt = Color.parseColor("#" + couleurs.get(i));
            boutonCouleur.getBackground().setTint(couleurInt);

            // Convertir de dp à px
            float scale = getResources().getDisplayMetrics().density;
            int widthInPixels = (int) (50 * scale + 0.5f);
            int heightInPixels = (int) (50 * scale + 0.5f);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    widthInPixels, heightInPixels
            );
            layoutParams.setMargins(0, 0, 0, 10);
            boutonCouleur.setLayoutParams(layoutParams);
            lvCouleursDisponibles.addView(boutonCouleur);
            }
    }

    public void afficherCodeSecret() throws JSONException, IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Afficher les couleurs
                for (int i = 0; i < longueurCode; i++) {
                    final Button bouton = new Button(Jouer.this);
                    bouton.setBackgroundResource(R.drawable.bouton_oval);

                    final int couleurInt = Color.parseColor("#EAEAEA");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Convertir de dp à px
                            float scale = getResources().getDisplayMetrics().density;
                            int widthInPixels = (int) (30 * scale + 0.5f);
                            int heightInPixels = (int) (30 * scale + 0.5f);

                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                    widthInPixels, heightInPixels  );
                            layoutParams.setMargins(0, 0, 10, 0);
                            bouton.setLayoutParams(layoutParams);
                        //    bouton.getBackground().setTint(couleurInt);
                            lvCodeSecret.addView(bouton);
                        }
                    });
                }
            }
        }).start();
    }

    public void afficherGrille() throws JSONException, IOException {

        // Définir les variables pour le nombre de lignes et de colonnes
        int nbLignes = nbMaxDeTentative;
        int nbColomnes = longueurCode;
        new Thread(new Runnable() {
            @Override
            public void run() {
                grille.setColumnCount(nbColomnes);
                grille.setRowCount(nbLignes);

                grille.post(new Runnable() {
                    @Override
                    public void run() {
                        int availableWidth = grille.getWidth();
                        int availableHeight = grille.getHeight();

                        int buttonWidth = (availableWidth - (20 * (nbColomnes + 1))) / nbColomnes; // Subtract the total margin space
                        int buttonHeight = (availableHeight - (20 * (nbLignes + 1))) / nbLignes; // Subtract the total margin space

                        if (buttonHeight < buttonWidth) {
                            buttonWidth = buttonHeight;
                        }

                        else {
                            buttonHeight = buttonWidth;
                        }

                        for (int j = 0; j < nbLignes; j++) {
                            for (int i = 0; i < nbColomnes; i++) {
                                final Button bouton = new Button(Jouer.this);
                                bouton.setBackgroundResource(R.drawable.bouton_oval);
                                final int couleurInt = Color.parseColor("#EAEAEA");

                                int finalButtonWidth = buttonWidth;
                                int finalButtonHeight = buttonHeight;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                                        layoutParams.width = finalButtonWidth;
                                        layoutParams.height = finalButtonHeight;
                                        layoutParams.setMargins(20, 0, 20, 20);

                                        bouton.setLayoutParams(layoutParams);
                                        bouton.getBackground().setTint(couleurInt);
                                        grille.addView(bouton);
                                    }
                                });
                            }
                        }
                    }
                });
            }
        }).start();
    }

    public void afficherTentative(int longueurCode) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < longueurCode; i++) {
                    Button bouton = new Button(Jouer.this);

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {


                            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();

                            layoutParams.setMargins(20, 0, 20, 20);

                            bouton.setLayoutParams(layoutParams);
                          //  bouton.getBackground().setTint(couleurInt);
                            lvTentatives.addView(bouton);
                        }
                    });
                }}
            }).start();
    }




}