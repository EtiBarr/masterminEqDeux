package com.example.mastermind.modele;

import java.util.ArrayList;

public class Feedback {

    private int couleurPosition;
    private int couleur;

    public Feedback(){ //constructeur par default
        this.couleur = 0;
        this.couleurPosition = 0;
    }
    public Feedback(Code code1, Code code2) {

        if (code1.longueurCode != code2.getLongueurCode()) {
            throw new RuntimeException("Les deux codes doivent avoir la même taille");
        }

        int nbCouleur = 0;
        int nbCouleurPosition = 0;
        int taille = code1.getLongueurCode();

        // Copie des tableaux
        ArrayList<String> code1Copie = code1.getCouleurs();
        ArrayList<String> code2Copie = code2.getCouleurs();

        for (int i = 0; i < taille; i++) {

            // Vérifier si les couleurs sont identiques à la même position
            if (code1Copie.get(i) == code2Copie.get(i) ) {
                nbCouleurPosition++;

                // Supprimer la couleur du tableau
                code1Copie.remove(i);
                code2Copie.remove(i);
            }
        }

        // Trouver les pairs de couleurs correspondantes dans le reste des tableaux
        for (int j = 0; j < code1Copie.size(); j++) {

            for (int k = 0; k < code2Copie.size(); k++) {
                if (code1Copie.get(j) == code2Copie.get(k)) {

                    nbCouleur++;
                    code1Copie.remove(j);
                    code2Copie.remove(k);
                }
            }
        }

        this.couleur = nbCouleur;
        this.couleurPosition = nbCouleurPosition;
    }

}
