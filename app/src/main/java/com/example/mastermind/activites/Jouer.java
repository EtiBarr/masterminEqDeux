package com.example.mastermind.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mastermind.R;
import com.example.mastermind.dao.MastermindDao;
import com.example.mastermind.dao.bdSQLite;
import com.example.mastermind.modele.Code;
import com.example.mastermind.modele.Feedback;
import com.example.mastermind.modele.Mastermind;
import com.example.mastermind.modele.RecordCode;
import com.example.mastermind.presentateur.PresenteurMastermind;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class Jouer extends AppCompatActivity implements View.OnClickListener {

    private String courriel;
    private int longueurCode;
    private int nbCouleurs;
    private int nbMaxDeTentative;
    private GridLayout grille;
    private LinearLayout lvCodeSecret;
    private LinearLayout lvCouleursDisponibles;

    private LinearLayout lvTentatives;
    private Button btnConfirmer;
    private Button btnNouvellePartie;
    private Button btnAccueil;
    private Button btnAbandon;
    private ImageButton btnInfo;
    private TextView record;
    private Mastermind partie;
    private boolean partieTerminee = false;
    private final int NB_NOUVELLE_PARTIE = 0;
    private final int NB_ABANDON = 1;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOM = "partie.db";
    private bdSQLite baseDeDonneeSQLite;


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
            longueurCode = intent.getIntExtra("longueurCode", 4)  ; // valeur par defaut si le il y a un probleme avec le intent
            nbCouleurs = intent.getIntExtra("nbCouleurs", 8);
            nbMaxDeTentative = intent.getIntExtra("nbMaxDeTentative", 10);
            // Displaying a toast message with all the received variables
            String message = "Email: " + courriel + "\n"
                    + "Longueur du Code: " + longueurCode + "\n"
                    + "Nombre de Couleurs: " + nbCouleurs + "\n"
                    + "Nombre Max de Tentative: " + nbMaxDeTentative;
          //  Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Intent is null", Toast.LENGTH_SHORT).show();
        }

        grille = findViewById(R.id.glJeu);
        lvCouleursDisponibles = findViewById(R.id.lvCouleursDisponibles);
        lvCodeSecret = findViewById(R.id.lvCodeSecretJouer);
        lvTentatives = findViewById(R.id.lvTentative);

        btnConfirmer = findViewById(R.id.btnConfirmer);
        btnConfirmer.setOnClickListener(this);

        btnNouvellePartie = findViewById(R.id.btnNouvellePartie);
        btnNouvellePartie.setOnClickListener(this);

        btnAccueil = findViewById(R.id.bAccueilJ);
        btnAccueil.setOnClickListener(this);

        btnAbandon = findViewById(R.id.btnAbandon);
        btnAbandon.setOnClickListener(this);

        btnInfo = findViewById(R.id.IBinfo);
        btnInfo.setOnClickListener(this);


        record = findViewById(R.id.tvRecord);

        presenteurMastermind = new PresenteurMastermind(this);
        presenteurMastermind.initializer(nbCouleurs, longueurCode);
    }

    @Override
    public void onClick(View v) {

        if (v == btnConfirmer) {
            if (!partieTerminee) {
                presenteurMastermind.ajouterTentative();
            }
        }

        else if (v == btnNouvellePartie) {

            if (partie.getNbTentatives() == 0 || partieTerminee) {
                presenteurMastermind.initializer(nbCouleurs, longueurCode);
            }

            else {
                showConfirmationDialog("Vous allez perdre la partie en cours, êtes-vous sûr de vouloir continuer?", NB_NOUVELLE_PARTIE);
            }
        }

        else if (v == btnAccueil) {
            finish();
        }

        else if (v == btnAbandon) {

            if (!partieTerminee) {
                showConfirmationDialog("Êtes-vous sûr de vouloir abandoner?", NB_ABANDON);
            }
        }

        else if (v == btnInfo) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Un cercle noir indique que vous avez correctement placé une couleur, tandis qu'un cercle blanc indique une bonne couleur à la mauvaise position.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void showConfirmationDialog(String message, int  operation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (operation == NB_NOUVELLE_PARTIE) {
                            presenteurMastermind.initializer(nbCouleurs, longueurCode);
                        }

                        else if (operation == NB_ABANDON) {
                            presenteurMastermind.abandoner();
                        }
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }


    public void afficherCouleursDisponible() throws JSONException, IOException {

        lvCouleursDisponibles.removeAllViews();

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

            final int finalCouleurInt = couleurInt; // Finalize the color for use in the OnClickListener

            // Ajouter un écouteur
            boutonCouleur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!partieTerminee) {
                        presenteurMastermind.ajouterCouleur(couleurInt);
                    }
                }
            });
        }
    }

    public void afficherCodeSecret() throws JSONException, IOException {


        lvCodeSecret.removeAllViews();

        // Afficher les couleurs
        for (int i = 0; i < longueurCode; i++) {
            final Button bouton = new Button(Jouer.this);
            bouton.setBackgroundResource(R.drawable.bouton_oval);


            // Convertir de dp à px
            float scale = getResources().getDisplayMetrics().density;
            int widthInPixels = (int) (30 * scale + 0.5f);
            int heightInPixels = (int) (30 * scale + 0.5f);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    widthInPixels, heightInPixels  );
            layoutParams.setMargins(0, 0, 10, 0);
            bouton.setLayoutParams(layoutParams);
            bouton.getBackground().setTint(Color.GRAY);
            lvCodeSecret.addView(bouton);
        }
    }

    public void afficherGrille() throws JSONException, IOException {

        partieTerminee = false;

        grille.removeAllViews();

        partie = presenteurMastermind.getMastermind();

        int marge = 20;
        if (longueurCode == 6) {
            marge = 10;
        }

        // Définir les variables pour le nombre de lignes et de colonnes
        int nbLignes = nbMaxDeTentative;
        int nbColomnes = longueurCode+1;

        System.out.println("nblignes : " + nbMaxDeTentative);

        grille.setColumnCount(nbColomnes);
        grille.setRowCount(nbLignes);

        int availableWidth = grille.getWidth();
        int availableHeight = grille.getHeight();

        int buttonWidth = (availableWidth - (20 * (nbColomnes))) / nbColomnes; // Subtract the total margin space
        int buttonHeight = (availableHeight - (20 * (nbLignes))) / nbLignes; // Subtract the total margin space

        if (buttonHeight < buttonWidth) {
            buttonWidth = buttonHeight;
        }

        else {
            buttonHeight = buttonWidth;
        }

        for (int j = 0; j < nbLignes; j++) {
            for (int i = 0; i < nbColomnes-1; i++) {

                final Button bouton = new Button(Jouer.this);
                bouton.setBackgroundResource(R.drawable.bouton_oval);
                final int couleurInt = Color.GRAY;

                int finalButtonWidth = buttonWidth;
                int finalButtonHeight = buttonHeight;

                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.width = finalButtonWidth;
                layoutParams.height = finalButtonHeight;
                layoutParams.setMargins(marge, 0, 0, marge);

                bouton.setLayoutParams(layoutParams);
                bouton.getBackground().setTint(couleurInt);
                bouton.setTag(couleurInt);
                grille.addView(bouton);
            }

            // La dernière colomne est pour un feedback
            GridLayout grilleFeedback = new GridLayout(this);
            grilleFeedback.setRowCount(longueurCode / 2);
            grilleFeedback.setColumnCount((int) Math.ceil((double) longueurCode / 2));

            System.out.println("la grille fait  " + grilleFeedback.getColumnCount());
            GridLayout.LayoutParams layoutParamsFeedback = new GridLayout.LayoutParams();
            layoutParamsFeedback.width = buttonWidth;
            layoutParamsFeedback.height = buttonHeight;
            layoutParamsFeedback.setMargins(marge, 0, 0, 0);

            grilleFeedback.setLayoutParams(layoutParamsFeedback);

            for (int k = 0; k < longueurCode; k++) {

                final Button boutonFeedback = new Button(Jouer.this);
                boutonFeedback.setBackgroundResource(R.drawable.bouton_oval);
                final int couleurInt = Color.GRAY;

                int largeurFeedback = buttonWidth / grilleFeedback.getColumnCount();
                int hauteurFeedback = buttonHeight / 2;

                if (largeurFeedback < hauteurFeedback) {
                    hauteurFeedback = largeurFeedback;
                }

                else if (hauteurFeedback < largeurFeedback) {
                    largeurFeedback = hauteurFeedback;
                }

                GridLayout.LayoutParams layoutParamsBoutonFeedback = new GridLayout.LayoutParams();
                layoutParamsBoutonFeedback.width = largeurFeedback;
                layoutParamsBoutonFeedback.height = hauteurFeedback;
                layoutParamsBoutonFeedback.setMargins(0, 0, 0, 0);

                boutonFeedback.setLayoutParams(layoutParamsBoutonFeedback);
                boutonFeedback.getBackground().setTint(couleurInt);
                boutonFeedback.setTag(couleurInt);
                grilleFeedback.addView(boutonFeedback);
            }

            grille.addView(grilleFeedback);
        }
    }

    public void afficherTentative() {

        lvTentatives.removeAllViews();

        for (int i = 0; i < longueurCode; i++) {
            Button bouton = new Button(Jouer.this);
            bouton.setBackgroundResource(R.drawable.bouton_oval);
            bouton.getBackground().setTint(Color.GRAY);

            int availableWidth = lvTentatives.getWidth();
            int availableHeigth = lvTentatives.getHeight();

            int buttonWidth = (availableWidth - (20 * (longueurCode))) / longueurCode; // Subtract the total margin space
            int buttonHeight = availableHeigth;

            if (buttonHeight < buttonWidth) {
                buttonWidth = buttonHeight;
            }

            else {
                buttonHeight = buttonWidth;
            }
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = buttonWidth;
            layoutParams.height = buttonHeight;
            layoutParams.setMargins(0, 0, 20, 0);

            bouton.setLayoutParams(layoutParams);
            bouton.setTag(Color.GRAY);

            lvTentatives.addView(bouton);

            bouton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bouton.setTag(Color.GRAY);
                    bouton.getBackground().setTint(Color.GRAY);
                }
            });

        }
    }

    public void afficherRecord() {

        if (presenteurMastermind.getRecord() != null) {
            record.setText(presenteurMastermind.getRecord().toString());
        }

        else {
            record.setText("Personne n'a encore trouvé ce code");
        }
    }

    public void ajouterCouleurTentative(int couleur) {

        int compteur = 0;
        boolean couleurAjoute = false;

        while (compteur < lvTentatives.getChildCount() && !couleurAjoute) {

            Button bouton = (Button) lvTentatives.getChildAt(compteur);
            int couleurBouton = (int) bouton.getTag();

            if (couleurBouton == Color.GRAY) {
                bouton.getBackground().setTint(couleur);
                bouton.setTag(couleur);
                couleurAjoute = true;
            }

            compteur++;
        }
    }

    public void ajouterTentative() {

        ArrayList<String> nouvelleTentative = new ArrayList<>();

        boolean tentativeRemplie = true;
        for (int i = 0; i < lvTentatives.getChildCount(); i++) {

            Button bouton = (Button) lvTentatives.getChildAt(i);
            int couleurBouton = (int) bouton.getTag();

            if (couleurBouton == Color.GRAY) {
                tentativeRemplie = false;
            }
        }

        if (tentativeRemplie) {

            // Trouver la première rangée de la grille non remplie en partant de la grille
            Button debutGrille = (Button) grille.getChildAt(0);

            int index = 0;
            while ((int) debutGrille.getTag() != Color.GRAY) {
                debutGrille = (Button) grille.getChildAt(index+longueurCode+1);
                index += longueurCode+1;
            }

            for (int i = 0; i < lvTentatives.getChildCount(); i++) {

                Button bouton = (Button) lvTentatives.getChildAt(i);
                int couleurBouton = (int) bouton.getTag();

                Button boutonGrille = (Button) grille.getChildAt(index);
                boutonGrille.getBackground().setTint(couleurBouton);
                boutonGrille.setTag(couleurBouton);

                // Réinitialiser la tentative
                bouton.setTag(Color.GRAY);
                bouton.getBackground().setTint(Color.GRAY);

                String couleurString = Integer.toHexString(couleurBouton);

                nouvelleTentative.add(couleurString);
                index++;
            }

            // Création d'un feedback
            Code codeTentative = new Code(longueurCode, 0, nouvelleTentative);
            Feedback nouveauFeedback = new Feedback(codeTentative, partie.getCode());

            GridLayout grilleFeedback = (GridLayout) grille.getChildAt(index);

            for (int i = 0; i < nouveauFeedback.getCouleurPosition(); i++) {
                Button feedbackBouton = (Button) grilleFeedback.getChildAt(i);
                feedbackBouton.getBackground().setTint(Color.BLACK);
            }

            for (int i = 0; i < nouveauFeedback.getCouleur(); i++) {
                Button feedbackBouton = (Button) grilleFeedback.getChildAt(nouveauFeedback.getCouleurPosition() + i);
                feedbackBouton.getBackground().setTint(Color.WHITE);
            }

            // Update Mastermind
            partie.ajouterTentative(nouvelleTentative, nouveauFeedback);

            if (nouveauFeedback.getCouleurPosition() == longueurCode) {
                presenteurMastermind.gagnerPartie();
            }

            else if (partie.getNbTentatives() == nbMaxDeTentative) {
                presenteurMastermind.perdrePartie();
            }
        }

        else {
            Toast.makeText(getApplicationContext(), "Vous devez remplir la tentative", Toast.LENGTH_SHORT).show();
        }
    }


    public void gagnerPartie() throws JSONException, IOException {

        envoyerDonneesPartie("Gagné");
        comparerRecord(partie.getCode().getId(),partie.getNbTentatives());

        partieTerminee = true;

        partie = presenteurMastermind.getMastermind();

        for (int i = 0; i < longueurCode; i++) {

            Button bouton = (Button) lvCodeSecret.getChildAt(i);
            String couleur = partie.getCode().getCouleurAtPosition(i);

            bouton.getBackground().setTint(Color.parseColor("#" + couleur));
        }
        Toast.makeText(getApplicationContext(), "Félicitations! Vous avez gagné", Toast.LENGTH_LONG).show();
    }

    public void perdrePartie() {

        envoyerDonneesPartie("Perdu");

        partieTerminee = true;

        partie = presenteurMastermind.getMastermind();
        System.out.println("code partie = " +partie.getCode());

        for (int i = 0; i < longueurCode; i++) {

            Button bouton = (Button) lvCodeSecret.getChildAt(i);
            String couleur = partie.getCode().getCouleurAtPosition(i);

            bouton.getBackground().setTint(Color.parseColor("#" + couleur));
        }
        Toast.makeText(getApplicationContext(), "Vous avez perdu!", Toast.LENGTH_LONG).show();
    }

    public void abandoner() {
        envoyerDonneesPartie("Abandon");

        partieTerminee = true;

        partie = presenteurMastermind.getMastermind();

        for (int i = 0; i < longueurCode; i++) {

            Button bouton = (Button) lvCodeSecret.getChildAt(i);
            String couleur = partie.getCode().getCouleurAtPosition(i);

            bouton.getBackground().setTint(Color.parseColor("#" + couleur));
        }
    }

    public void envoyerDonneesPartie(String message) {

        System.out.println(partie.getCode().afficherCouleurs());
        baseDeDonneeSQLite = new bdSQLite(this, DATABASE_NOM, null, DATABASE_VERSION);


        baseDeDonneeSQLite.ajouterPartie(courriel, partie.getCode().afficherCouleurs(), nbCouleurs, message, partie.getNbTentatives(),partie.getCode().getId());
    }

    public void comparerRecord(int id, int nbTentatives) throws JSONException, IOException {

        RecordCode recordCourant = presenteurMastermind.getRecord();

        if (recordCourant == null) {
            presenteurMastermind.creerRecord(id, nbTentatives, courriel, presenteurMastermind.getIdStat());
        }

        else if (recordCourant.getNbTentatives() > partie.getNbTentatives()) {
            presenteurMastermind.changerRecord(id, nbTentatives, courriel);
        }
    }






}