package com.example.mastermind.modele;

import java.util.ArrayList;

public class Modele {

    private ArrayList<String> couleurs;
    private RecordCode record;
    private Code code;
    private Mastermind partie;
    private int prochainIdRecord;

    public Code getCode() {
        return code;
    }

    public RecordCode getRecord() {
        return record;
    }

    public ArrayList<String> getCouleurs() {
        return couleurs;
    }

    public Mastermind getMastermind() {
        return partie;
    }

    public void setCouleurs(ArrayList<String> couleurs) {
        this.couleurs = couleurs;
    }

    public void setMastermind(Mastermind partie) {
        this.partie = partie;
    }

    public void setRecord(RecordCode record) {
        this.record = record;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public void setProchainIdRecord(int prochainIdRecord) {
        this.prochainIdRecord = prochainIdRecord;
    }

    public int getProchainIdRecord() {
        return prochainIdRecord;
    }
}

