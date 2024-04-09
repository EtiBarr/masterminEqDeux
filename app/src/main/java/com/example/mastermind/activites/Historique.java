package com.example.mastermind.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mastermind.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Historique extends AppCompatActivity implements View.OnClickListener {
    private Button revenir;
    private ListView lvPartiesH;
    private SimpleAdapter adaptateur;
    private List<Map<String, String>> listeParties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        // Initialiser les éléments
        revenir = findViewById(R.id.bRevenirH);
        revenir.setOnClickListener(this);

        lvPartiesH = findViewById(R.id.lvPartiesH);
        listeParties = new ArrayList<>();

        // Ajouter des données d'exemple
        creerNouvellePartie();
        creerNouvellePartie();

        // Créer les données nécessaires
        String[] donnees = {"courriel", "courrielInput",
                         "code", "codeInput",
                         "couleurs", "couleursInput",
                         "resultat", "resultatInput",
                         "tentatives", "tentativesInput"};

        // Disposition des données
        int[] disposition = {R.id.tvCourrielH, R.id.tvInputCourrielH,
                    R.id.tvCodeH, R.id.tvInputCodeH,
                    R.id.tvCouleursH, R.id.tvInputCouleursH,
                    R.id.tvResultatH, R.id.tvInputResultatH,
                    R.id.tvTentativesH, R.id.tvInputTentativesH};

        // Initialiser l'adaptateur
        adaptateur = new SimpleAdapter(this, listeParties, R.layout.parties_layout, donnees, disposition);
        lvPartiesH.setAdapter(adaptateur);
    }

    @Override
    public void onClick(View v) {
        if (v == revenir) {
            finish();
        }
    }

    private void creerNouvellePartie() {
        // Créer une nouvelle partie (données)
        Map<String, String> partieData = new HashMap<>();
        partieData.put("courriel", "Le courriel du joueur");
        partieData.put("courrielInput", "example@adresse.courriel");

        partieData.put("code", "Le code secret");
        partieData.put("codeInput", "4");

        partieData.put("couleurs", "Le nombre de couleurs");
        partieData.put("couleursInput", "8");

        partieData.put("resultat", "Le résultat de la partie");
        partieData.put("resultatInput", "Réussie");

        partieData.put("tentatives", "Le nombre de tentatives");
        partieData.put("tentativesInput", "10");


        // Ajouter la partie à la liste
        listeParties.add(partieData);
    }
}