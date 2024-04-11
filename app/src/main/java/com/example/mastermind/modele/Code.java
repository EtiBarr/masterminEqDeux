package com.example.mastermind.modele;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Code {

    ArrayList<String> couleurs;
    int longueurCode;
    int id;

    public Code(){ // constructeur par default
        this.longueurCode = 0;
        this.id = 0;
        this.couleurs = new ArrayList<>();
    }

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

    public String afficherCouleurs() {

        String couleursCode = "";
        for (int i = 0; i < this.longueurCode; i++) {

            switch (couleurs.get(i)) {
                case "ffff0000":
                    couleursCode += "Rouge, ";
                    break;
                case "ff00ff00":
                    couleursCode += "Vert, ";
                    break;
                case "ff0000ff":
                    couleursCode += "Bleu, ";
                    break;
                case "ffffff00":
                    couleursCode += "Jaune, ";
                    break;
                case "ffff00ff":
                    couleursCode += "Magenta, ";
                    break;
                case "ffffa500":
                    couleursCode += "Orange,  ";
                    break;
                case "ff000000":
                    couleursCode += "Noir, ";
                    break;
                case "ffffffff":
                    couleursCode += "Blanc, ";
                    break;
            }
        }

        return couleursCode;
    }


}
