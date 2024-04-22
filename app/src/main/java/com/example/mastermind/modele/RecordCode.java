package com.example.mastermind.modele;

public class RecordCode {

    private int nbTentatives;
    private String courriel;
    private int idCode;

    public RecordCode(){ //constructeur par default
        this.nbTentatives = 0;
        this.courriel = "no email";
        this.idCode = 0;
    }

    public RecordCode(int nbTentatives, String courriel, int idCode) {

        this.nbTentatives = nbTentatives;
        this.courriel = courriel;
        this.idCode = idCode;
    }
    public int getIdCode() {
        return idCode;
    }

    public String getCourriel() {
        return courriel;
    }

    public int getNbTentatives() {
        return nbTentatives;
    }

    public String toString() {
        return "\nCourriel : " + this.getCourriel() + "\tTentatives : " + this.getNbTentatives() + "\n";
    }
}
