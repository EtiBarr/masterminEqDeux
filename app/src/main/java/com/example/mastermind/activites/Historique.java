package com.example.mastermind.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mastermind.R;
import com.example.mastermind.adaptateur.PartieAdaptateur;
import com.example.mastermind.dao.bdSQLite;

import java.util.ArrayList;
import java.util.HashMap;

public class Historique extends AppCompatActivity implements View.OnClickListener {
    private Button revenir;
    private ListView lvPartiesH;
    private PartieAdaptateur adaptateur;
    private ArrayList<HashMap<String, String>> listeParties;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOM = "partie.db";
    private bdSQLite baseDeDonneeSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        // Initialiser les éléments
        revenir = findViewById(R.id.bRevenirH);
        revenir.setOnClickListener(this);

        lvPartiesH = findViewById(R.id.lvPartiesH);
        listeParties = new ArrayList<>();

        baseDeDonneeSQLite = new bdSQLite(this, DATABASE_NOM, null, DATABASE_VERSION);
        ArrayList<HashMap<String, String>> liste = baseDeDonneeSQLite.retournerListePartie();

        for (int i = liste.size()-1; i >= 0; i--) {

            String courriel = liste.get(i).get("Email");
            String code = liste.get(i).get("Code secret");
            String couleurs = liste.get(i).get("nombre de couleurs");
            String resultat = liste.get(i).get("Resultat");
            String tentatives = liste.get(i).get("Nombre de tentatives");

            creerNouvellePartie(courriel, code, couleurs, resultat, tentatives);
        }

        // Créer les données nécessaires
        String[] donnees = {"courrielInput",
                            "codeInput",
                            "couleursInput",
                            "resultatInput",
                            "tentativesInput"};

        // Disposition des données
        int[] disposition = {R.id.tvInputCourrielH,
                             R.id.tvInputCodeH,
                             R.id.tvInputCouleursH,
                             R.id.tvInputResultatH,
                             R.id.tvInputTentativesH};

        // Initialiser l'adaptateur
        adaptateur = new PartieAdaptateur(this,
                                            listeParties,
                                            R.layout.parties_layout,
                                            donnees, disposition);
        lvPartiesH.setAdapter(adaptateur);
    }

    @Override
    public void onClick(View v) {
        if (v == revenir) {
            finish();
        }
    }

    private void creerNouvellePartie(String courriel, String code, String couleurs, String resultat, String tentatives) {
        // Créer une nouvelle partie (données)
        HashMap<String, String> partieData = new HashMap<>();
        partieData.put("courrielInput", courriel);
        partieData.put("codeInput", code);
        partieData.put("couleursInput", couleurs);
        partieData.put("resultatInput", resultat);
        partieData.put("tentativesInput", tentatives);

        // Ajouter la partie à la liste
        listeParties.add(partieData);
    }
}