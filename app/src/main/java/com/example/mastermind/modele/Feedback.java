package com.example.mastermind.modele;

import java.util.ArrayList;
import java.util.Objects;

public class Feedback {

    private int couleurPosition;
    private int couleur;

    public Feedback(){ //constructeur par default
        this.couleur = 0;
        this.couleurPosition = 0;
    }
    public Feedback(Code code1, Code code2) {

        System.out.println("code1 est : " + code1);
        System.out.println("code1 est : " + code2);


        if (code1.longueurCode != code2.getLongueurCode()) {
            throw new RuntimeException("Les deux codes doivent avoir la même taille");
        }

        int nbCouleur = 0;
        int nbCouleurPosition = 0;
        int taille = code1.getLongueurCode();

        // Copie des tableaux
        ArrayList<String> code1Copie = new ArrayList<>(code1.getCouleurs());
        ArrayList<String> code2Copie = new ArrayList<>(code2.getCouleurs());

        for (int i = 0; i < taille; i++) {

            // Vérifier si les couleurs sont identiques à la même position
            if (Objects.equals(code1Copie.get(i), code2Copie.get(i))) {
                nbCouleurPosition++;

                // Supprimer la couleur du tableau
                code1Copie.set(i, null);
                code2Copie.set(i, null);
            }
        }

        // Trouver les pairs de couleurs correspondantes dans le reste des tableaux
        for (int i = 0; i < taille; i++) {
            if (code1Copie.get(i) != null) {
                for (int j = 0; j < taille; j++) {
                    if (i != j && code2Copie.get(j) != null && Objects.equals(code1Copie.get(i), code2Copie.get(j))) {
                        nbCouleur++;

                        code1Copie.set(i, null);
                        code2Copie.set(j, null);
                        break;
                    }
                }
            }
        }

        this.couleur = nbCouleur;
        this.couleurPosition = nbCouleurPosition;
    }

    public int getCouleur() {
        return couleur;
    }

    public int getCouleurPosition() {
        return couleurPosition;
    }
}
