package com.example.mastermind.modele;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Code {

    ArrayList<String> couleurs;
    int longueurCode;
    int id;


    public Code(int longueur, int id, ArrayList<String> couleurs) {

        this.longueurCode = longueur;
        this.id = id;
        this.couleurs = couleurs;
    }

    public ArrayList<String> getCouleurs() {
        return this.couleurs;
    }

    public int getLongueurCode() {
        return this.longueurCode;
    }
    public int getId() {return this.id;
    }


    public String getCouleurAtPosition(int index) {
        return couleurs.get(index);
    }

    @NonNull
    public String toString() {
        return "id = " + this.getId() + "\tnbCouleurs = " + this.getLongueurCode() + "\tCode = " + this.getCouleurs() + "\n";
    }


}
