package com.example.mastermind.modele;

import java.util.ArrayList;
import java.util.List;

public class Code {

    ArrayList<String> couleurs;
    int longueurCode;

    public Code(int longueur) {

        this.longueurCode = longueur;

        // Le code est choisie aléatoirement dans la base de données
    }

    public ArrayList<String> getCode() {
        return this.couleurs;
    }

    public int getLongueurCode() {
        return this.longueurCode;
    }

    public String getCouleurAtPosition(int index) {
        return couleurs.get(index);
    }


}
