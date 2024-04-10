package com.example.mastermind.modele;

import java.util.ArrayList;

public class Modele {

    private ArrayList<String> couleurs;
    private RecordCode record;
    private Code code;

    public Code getCode() {
        return code;
    }

    public RecordCode getRecord() {
        return record;
    }

    public ArrayList<String> getCouleurs() {
        return couleurs;
    }

    public void setCouleurs(ArrayList<String> couleurs) {
        this.couleurs = couleurs;
    }

    public void setRecord(RecordCode record) {
        this.record = record;
    }

    public void setCode(Code code) {
        this.code = code;
    }
}
