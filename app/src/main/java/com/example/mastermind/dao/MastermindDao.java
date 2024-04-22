package com.example.mastermind.dao;

import com.example.mastermind.modele.Code;
import com.example.mastermind.modele.RecordCode;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MastermindDao {

    // Retourne un code aléatoire selon les critères demandés
    public static Code obtenirCode(int nbCouleurs, int longueurCode) throws JSONException, IOException {

        ArrayList<Code> listeCodes = HttpJsonService.obtenirCodeSecret(nbCouleurs);

        // Enlever tous les codes qui ne sont pas de la longueur désirée
        listeCodes.removeIf(code -> code.getLongueurCode() != longueurCode);

        // Choisir un code aléatoire
        int nombreAleatoire = (int) (Math.random() * listeCodes.size()) ;
        return listeCodes.get(46);
    }

    // Retourne le record pour un code
    public static RecordCode obtenirRecord(Code code) throws JSONException, IOException {
        return HttpJsonService.obtenirRecord(code.getId());
    }

    // Retourne les couleurs désirées
    public static ArrayList<String> obtenirCouleurs(int nbCouleurs) throws JSONException, IOException {
        ArrayList<String> couleurs = HttpJsonService.obtenirCouleurs();
        ArrayList<String> couleursChoisies = new ArrayList<>();

        for (int i = 0; i < couleurs.size(); i++) {
            couleursChoisies.add(couleurs.get(i));
        }

        return couleursChoisies;
    }

    public static int obtenirDernierId() throws JSONException, IOException {
        return HttpJsonService.obtenirDernierId();
    }

    public static void creerRecord(int id, int nbTentatives, String courriel, int idStat) throws JSONException, IOException {

        HttpJsonService.creerRecord(id, nbTentatives, courriel, idStat);
    }

    public static void changerRecord(int id, int nbTentatives, String courriel) throws JSONException, IOException {

        HttpJsonService.changerRecord(id, nbTentatives, courriel);
    }
}
