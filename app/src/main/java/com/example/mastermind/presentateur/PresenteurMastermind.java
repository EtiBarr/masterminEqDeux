package com.example.mastermind.presentateur;


import android.app.Activity;

import com.example.mastermind.activites.Jouer;
import com.example.mastermind.dao.MastermindDao;
import com.example.mastermind.modele.Code;
import com.example.mastermind.modele.Feedback;
import com.example.mastermind.modele.Mastermind;
import com.example.mastermind.modele.Modele;
import com.example.mastermind.modele.ModeleManager;
import com.example.mastermind.modele.RecordCode;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PresenteurMastermind {
    private Activity activite;
    private Modele modele;

    public PresenteurMastermind(Activity activite){
        this.activite = activite;
        this.modele = ModeleManager.getModele();
    }
    public void initializer(int nbCouleurs, int longueurCode){

        new Thread() {
            @Override
            public void run() {

                try {

                    ArrayList<String> couleurs  = MastermindDao.obtenirCouleurs(nbCouleurs);
                    Code code = MastermindDao.obtenirCode(nbCouleurs, longueurCode);

                    modele.setCode(code);
                    modele.setCouleurs(couleurs);

                    Mastermind partie = new Mastermind(code);
                    modele.setMastermind(partie);

                    System.out.println("ok new approach:  " + modele.getCode());
                    ((Jouer)activite).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ((Jouer)activite).afficherCouleursDisponible();
                                ((Jouer)activite).afficherCodeSecret();
                                ((Jouer)activite).afficherGrille();                                ((Jouer)activite).afficherGrille();
                                ((Jouer)activite).afficherTentative();

                            } catch (JSONException | IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } catch (Exception e){
                e.printStackTrace();
                }
            }
        }.start();
    }

    public void ajouterCouleur(int couleur) {

        new Thread() {
            @Override
            public void run() {
                try {
                    ((Jouer)activite).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((Jouer)activite).ajouterCouleurTentative(couleur);
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void ajouterTentative() {
        new Thread() {
            @Override
            public void run() {
                try {
                    ((Jouer)activite).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((Jouer)activite).ajouterTentative();
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public ArrayList<String> obtenirCouleurs() {
        return modele.getCouleurs();
    }

    public Mastermind getMastermind() {
            System.out.print("ehhh   " + modele.getCode());
        return modele.getMastermind();
    }
}
