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


        runOnUiThread(new Runnable() {
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
        });
        presenteurMastermind = new PresenteurMastermind(this);
        presenteurMastermind.initializer();


/*
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
        }*/

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


        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<String> couleurs;
                try {
                    couleurs = MastermindDao.obtenirCouleurs(nbCouleurs);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

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
                });
            }
        }).start();
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